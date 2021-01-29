package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.exception.CurrencyNotFound;
import co.com.bancolombia.mscurrencytest.domain.model.dto.CurrencyDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.client.BNCService;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaCurrencyRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserCurrencyRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserRepository;
import co.com.bancolombia.mscurrencytest.mocks.DefaultAnswers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CurrencyServiceUnitTest extends DefaultAnswers {

    @MockBean
    JpaCurrencyRepository jpaCurrencyRepository;
    @MockBean
    JpaUserCurrencyRepository jpaUserCurrencyRepository;
    @MockBean
    JpaUserRepository jpaUserRepository;
    @MockBean
    BNCService bncService;

    CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        currencyService = new CurrencyServiceImpl(jpaCurrencyRepository, jpaUserCurrencyRepository, jpaUserRepository, bncService);
        mockSecurityContext();
        when(jpaUserCurrencyRepository.findByUserUsername(anyString())).thenReturn(mockUserCurrencies());
    }

    @Test
    void testGetTop3ShouldBringReversed() {
        List<CurrencyDTO> currencyDTOS = currencyService.getTop3(true);
        assertEquals(0, currencyDTOS.get(0)
                .getPrice()
                .compareTo(BigDecimal.valueOf(1000).multiply(BigDecimal.valueOf(0.57))));
        assertTrue(currencyDTOS.get(0).getPrice().compareTo(currencyDTOS.get(1).getPrice()) < 0);
    }

    @Test
    void testGetTop3ShouldBringOrdered() {
        List<CurrencyDTO> currencyDTOS = currencyService.getTop3(false);
        assertTrue(currencyDTOS.get(1).getPrice().compareTo(currencyDTOS.get(0).getPrice()) < 0);
    }

    @Test
    void testGetCurrenciesShouldBeOk() {
        List<CurrencyDTO> currencyDTOS = currencyService.getUserCurrencies();

        assertEquals(0, currencyDTOS.get(0)
                .getPrice()
                .compareTo(BigDecimal.valueOf(1000).multiply(BigDecimal.valueOf(0.57))));
        assertFalse(currencyService.getUserCurrencies().isEmpty());
    }

    @Test
    void testAddCurrencyShouldCallOnlyBd() throws CurrencyNotFound {
        when(jpaCurrencyRepository.findByCurrencyDto(any(CurrencyDTO.class))).thenReturn(mockCurrencies().stream()
                .findFirst());
        when(jpaUserRepository.findByUsername(anyString())).thenReturn(Optional.of(defaultUser()));
        currencyService.addCurrency(mockBitCoinCurrencyDto());
        verify(jpaCurrencyRepository, times(1)).save(Mockito.any());
        verify(jpaUserRepository, times(1)).save(Mockito.any());
    }

    @Test
    void testAddCurrencyShouldCallApiBnc() throws CurrencyNotFound {
        when(jpaCurrencyRepository.findByCurrencyDto(any())).thenReturn(Optional.empty());
        when(bncService.getToAssetTicker(anyString(), Mockito.anyBoolean())).thenReturn(mockTickerAnswer());
        when(bncService.getToAssetById(anyString())).thenReturn(mockAssetResponse().getContent().get(0));
        when(jpaUserRepository.findByUsername(anyString())).thenReturn(Optional.of(defaultUser()));
        currencyService.addCurrency(mockBitCoinCurrencyDto());
        verify(jpaCurrencyRepository, times(1)).save(Mockito.any());
        verify(jpaUserRepository, times(1)).save(Mockito.any());
    }
}

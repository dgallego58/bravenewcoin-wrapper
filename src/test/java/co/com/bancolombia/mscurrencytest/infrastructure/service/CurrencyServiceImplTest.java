package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.model.entities.Currency;
import co.com.bancolombia.mscurrencytest.domain.model.entities.User;
import co.com.bancolombia.mscurrencytest.infrastructure.client.BNCService;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaCurrencyRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserCurrencyRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//integration
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase
@Disabled
class CurrencyServiceImplTest {

    @Autowired
    JpaCurrencyRepository jpaCurrencyRepository;
    @Autowired
    JpaUserCurrencyRepository jpaUserCurrencyRepository;
    @Autowired
    JpaUserRepository jpaUserRepository;
    @Autowired
    BNCService bncService;

    CurrencyService currencyService;

    @BeforeEach
    @Transactional
    void setUp() {
        currencyService = new CurrencyServiceImpl(jpaCurrencyRepository, jpaUserCurrencyRepository, jpaUserRepository, bncService);
        Authentication authentication = Mockito.mock(Authentication.class);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("test");
        SecurityContextHolder.setContext(securityContext);
        saveDb();
    }

    private void saveDb() {

        User user = new User();
        user.setFirstname("TestFirstname").setLastname("TestLastname").setUsername("test").setPassword("testpsw");
        Currency currency = new Currency().setPrice(BigDecimal.valueOf(1000.00))
                .setApiIdentifier("api-identifier")
                .setSymbol("COP")
                .setStatus("ACITVE")
                .setType("FIAT")
                .setName("Colombian Peso");
        Currency currency2 = new Currency().setPrice(BigDecimal.valueOf(1000.00))
                .setApiIdentifier("api-identifier2")
                .setSymbol("USD")
                .setStatus("ACITVE")
                .setType("FIAT")
                .setName("United state dollar");
        //jpaCurrencyRepository.saveAll(List.of(currency, currency2));

        user.addCurrency(currency, true);
        user.addCurrency(currency2, false);
        System.out.println("LA COLECCION Está vacía?" + user.getUserCurrencies().isEmpty());
        jpaUserRepository.save(user);


    }

    @Test
    void getUserCurrencies() {

        assertNotNull(currencyService.getUserCurrencies());
    }

    @Test
    void getTop3() {
    }
}
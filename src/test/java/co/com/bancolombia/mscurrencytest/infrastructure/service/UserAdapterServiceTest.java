package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.exception.CurrencyNotFound;
import co.com.bancolombia.mscurrencytest.domain.model.dto.UserDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.client.BNCService;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaCurrencyRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.utils.Converter;
import co.com.bancolombia.mscurrencytest.mocks.DefaultAnswers;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class UserAdapterServiceTest extends DefaultAnswers {

    @MockBean
    JpaUserRepository jpaUserRepository;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    JpaCurrencyRepository jpaCurrencyRepository;
    @MockBean
    BNCService bncService;

    IUserGeneralService iUserGeneralService;

    @BeforeEach
    void setUp() {
        iUserGeneralService = new UserGeneralService(jpaUserRepository, passwordEncoder, bncService);
    }

    @Test
    void saveUser() throws JsonProcessingException, CurrencyNotFound {


        Mockito.when(bncService.getToAsset(Mockito.any())).thenReturn(mockAssetResponse());
        Mockito.when(bncService.getToAssetTicker(Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(mockTickerAnswer());

        UserDTO userDTO = UserDTO.builder()
                .password("testpass")
                .username("testusr")
                .firstname("test firstname")
                .lastname("test lastname")
                .favoriteCurrencySymbol("COP")
                .build();

        System.out.printf("REQUEST JSON: %s", Converter.configuredObjectMapper().writeValueAsString(userDTO));
        iUserGeneralService.saveUser(userDTO);
        Mockito.verify(jpaUserRepository, Mockito.times(1)).save(Mockito.any());
        //Mockito.verify(jpaCurrencyRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void whenLoadUserByUsernameShouldBeOk() {

        Mockito.when(jpaUserRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(defaultUser()));
        UserDetails userDetails = iUserGeneralService.loadUserByUsername("testusrname");
        assertEquals("testusrname", userDetails.getUsername());
    }

    @Test
    void whenLoadUserByUsernameShouldThrowUsernotfound() {
        Mockito.when(jpaUserRepository.findByUsername(Mockito.anyString())).thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> iUserGeneralService.loadUserByUsername("testusrname"));
    }


}
package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.model.dto.UserDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.utils.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserAdapterServiceTest {

    @MockBean
    JpaUserRepository jpaUserRepository;
    @MockBean
    PasswordEncoder passwordEncoder;

    IUserGeneralService userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserGeneralService(jpaUserRepository, passwordEncoder);
    }

    @Test
    void saveUser() throws JsonProcessingException {


        UserDTO userDTO = UserDTO.builder()
                .password("testpass")
                .username("testusr")
                .firstname("test firstname")
                .lastname("test lastname")
                .build();

        System.out.printf("REQUEST JSON: %s", Converter.configuredObjectMapper().writeValueAsString(userDTO));

        userRepository.saveUser(userDTO);

        Mockito.verify(jpaUserRepository, Mockito.times(1)).save(Mockito.any());
    }
}
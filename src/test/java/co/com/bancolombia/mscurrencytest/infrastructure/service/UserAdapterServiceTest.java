package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.exception.CurrencyNotFound;
import co.com.bancolombia.mscurrencytest.domain.model.dto.UserDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.client.BNCService;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.AssetDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.ContentGenericWrapper;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaCurrencyRepository;
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

import java.util.List;

@ExtendWith(SpringExtension.class)
class UserAdapterServiceTest {

    @MockBean
    JpaUserRepository jpaUserRepository;
    @MockBean
    PasswordEncoder passwordEncoder;

    IUserGeneralService userRepository;
    @MockBean
    JpaCurrencyRepository jpaCurrencyRepository;

    @MockBean
    BNCService bncService;

    @BeforeEach
    void setUp() {
        userRepository = new UserGeneralService(jpaUserRepository, passwordEncoder, bncService);
    }

    @Test
    void saveUser() throws JsonProcessingException, CurrencyNotFound {


        Mockito.when(bncService.getToAsset(Mockito.any())).thenReturn(mockAnswer());
        Mockito.when(bncService.getToAssetById(Mockito.any()))
                .thenReturn(mockAnswer().getContent().stream().findFirst().orElse(null));

        UserDTO userDTO = UserDTO.builder()
                .password("testpass")
                .username("testusr")
                .firstname("test firstname")
                .lastname("test lastname")
                .favoriteCurrencySymbol("COP")
                .build();

        System.out.printf("REQUEST JSON: %s", Converter.configuredObjectMapper().writeValueAsString(userDTO));

        userRepository.saveUser(userDTO);

        Mockito.verify(jpaUserRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(jpaCurrencyRepository, Mockito.times(1)).save(Mockito.any());
    }

    private ContentGenericWrapper<AssetDTO.AssetResponseDTO> mockAnswer() {
        AssetDTO.AssetResponseDTO assetResponseDTO = new AssetDTO.AssetResponseDTO("123", "Colombian Peso", "COP", "ACTIVE", "CRYPTO", "not prov");

        return new ContentGenericWrapper<>(List.of(assetResponseDTO));
    }
}
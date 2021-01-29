package co.com.bancolombia.mscurrencytest.mocks;

import co.com.bancolombia.mscurrencytest.domain.model.dto.CurrencyDTO;
import co.com.bancolombia.mscurrencytest.domain.model.entities.Currency;
import co.com.bancolombia.mscurrencytest.domain.model.entities.User;
import co.com.bancolombia.mscurrencytest.domain.model.entities.UserCurrency;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dto.AssetDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dto.AssetTickerResponse;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dto.ContentGenericWrapper;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dto.MarketDTO;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public abstract class DefaultAnswers {

    protected ContentGenericWrapper<AssetDTO.AssetResponseDTO> mockAssetResponse() {
        AssetDTO.AssetResponseDTO assetResponseDTO = new AssetDTO.AssetResponseDTO("f1ff77b6-3ab4-4719-9ded-2fc7e71cff1f", "Colombian Peso", "COP", "ACTIVE", "CRYPTO", "not prov");

        return new ContentGenericWrapper<>(List.of(assetResponseDTO));
    }

    protected ContentGenericWrapper<AssetTickerResponse> mockTickerAnswer() {
        AssetTickerResponse assetTickerResponse = new AssetTickerResponse("idTable", "assetTickerId", LocalDateTime.now(), 15, 10, BigDecimal
                .valueOf(32805.11), BigDecimal.valueOf(15.36), 156L, 13L, BigDecimal.valueOf(112.3), BigDecimal.valueOf(13.25), null, null, null, null);
        return new ContentGenericWrapper<>(List.of(assetTickerResponse));
    }

    protected AssetDTO.AssetRequestDTO createAssetRequest() {
        return AssetDTO.AssetRequestDTO.builder()
                .status(AssetDTO.AssetStatus.ACTIVE)
                .symbol("COP")
                .type("CRYPTO")
                .build();
    }

    protected MarketDTO marketRequestDTO() {
        return MarketDTO.builder().baseAssetId(null).quoteAssetId(null).build();
    }

    protected User defaultUser() {
        return new User().setPassword("testpsw")
                .setUsername("testusrname")
                .setFirstname("testfirstname")
                .setLastname("testlastname");
    }

    protected List<Currency> mockCurrencies() {
        Currency currency = new Currency().setId(1L)
                .setPrice(BigDecimal.valueOf(1000.00))
                .setAssetId("f1ff77b6-3ab4-4719-9ded-2fc7e71cff1f")
                .setSymbol("COP")
                .setStatus("ACTIVE")
                .setType("FIAT")
                .setName("Colombian Peso");
        Currency currency2 = new Currency().setId(2L)
                .setPrice(BigDecimal.valueOf(1500.00))
                .setAssetId("e77b9824-126a-418e-a69c-a2e682578e94")
                .setSymbol("USD")
                .setStatus("ACTIVE")
                .setType("FIAT")
                .setName("United States Dollar");
        return List.of(currency, currency2);
    }

    protected void mockSecurityContext() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("test");
        SecurityContextHolder.setContext(securityContext);
    }

    protected List<UserCurrency> mockUserCurrencies() {
        UserCurrency userCurrency1 = new UserCurrency(defaultUser(), mockCurrencies().get(0), true);
        UserCurrency userCurrency2 = new UserCurrency(defaultUser(), mockCurrencies().get(1), false);

        return List.of(userCurrency1, userCurrency2);
    }

    protected CurrencyDTO mockBitCoinCurrencyDto() {
        return CurrencyDTO.builder()
                .symbol("BTC")
                .assetId("f1ff77b6-3ab4-4719-9ded-2fc7e71cff1f")
                .price(BigDecimal.valueOf(1000.00))
                .type("CRYPTO")
                .status("ACTIVE")
                .name("Bitcoin")
                .build();
    }
}

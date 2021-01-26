package co.com.bancolombia.mscurrencytest.infrastructure.client;

import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.AssetDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.BNCTokenDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.ContentGenericWrapper;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.MarketDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class BraveNewCoinClientTest {

    @Autowired
    RestTemplate restTemplate;
    BNCService bncService;

    @BeforeEach
    void setUp() {
        bncService = new BraveNewCoinClient(restTemplate);
    }

    @Test
    void getToken() {
        BNCTokenDTO.ResponseGetTokenDTO token = bncService.getToken(bncService.defaultTokenDto());
        assertAll(() -> {
            System.out.printf("token is: %s", token.getAccessToken());
            assertNotNull(token.getAccessToken());
            assertTrue(token.getExpiresIn() <= 86400);
        });
    }

    @Test
    void getToMarket() {
        ContentGenericWrapper<MarketDTO.MarketResponseDTO> content = bncService.getToMarket(marketRequestDTO());
        assertNotNull(content.getContent());
    }

    @Test
    void getToAssetTest() {
        ContentGenericWrapper<AssetDTO.AssetResponseDTO> content = bncService.getToAsset(createAssetRequest());
        assertNotNull(content.getContent());
    }

    @Test
    void getToMarketTest() {
        ContentGenericWrapper<MarketDTO.MarketResponseDTO> content = bncService.getToMarket(marketRequestDTO());
        MarketDTO.MarketResponseDTO mr = content.getContent().get(0);
        String bAssetId = mr.getBaseAssetId();
        String qAssetId = mr.getQuoteAssetId();
        System.out.println(bAssetId);
        System.out.println(qAssetId);
        assertTrue(content.getContent().stream().findFirst().isPresent());
    }

    @Test
    void getToAssetOrMarketIdTest() {
        ContentGenericWrapper<AssetDTO.AssetResponseDTO> asset = bncService.getToAsset(createAssetRequest());
        ContentGenericWrapper<MarketDTO.MarketResponseDTO> market = bncService.getToMarket(marketRequestDTO());
        AssetDTO.AssetResponseDTO assetResponseDTO = asset.getContent().get(0);
        MarketDTO.MarketResponseDTO marketResponseDTO = market.getContent().get(0);

        AssetDTO.AssetResponseDTO assetById = bncService.getToAssetById(assetResponseDTO.getId());
        MarketDTO.MarketResponseDTO marketId = bncService.getToMarketById(marketResponseDTO.getId());
        assertAll(() -> {
            assertNotNull(assetResponseDTO);
            assertNotNull(marketResponseDTO);
            assertNotNull(assetById.getName());
            assertNotNull(marketId.getId());
        });
    }

    private AssetDTO.AssetRequestDTO createAssetRequest() {
        return AssetDTO.AssetRequestDTO.builder()
                .status(AssetDTO.AssetStatus.ACTIVE)
                .symbol("BTC")
                .type(AssetDTO.AssetType.CRYPTO)
                .build();
    }

    private MarketDTO marketRequestDTO() {
        return MarketDTO.builder().baseAssetId(null).quoteAssetId(null).build();
    }
}
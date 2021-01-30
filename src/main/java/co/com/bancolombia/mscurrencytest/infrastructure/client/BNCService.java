package co.com.bancolombia.mscurrencytest.infrastructure.client;

import co.com.bancolombia.mscurrencytest.infrastructure.client.constants.ClientConstants;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dto.*;

public interface BNCService {

    String BNC_URL = "https://bravenewcoin.p.rapidapi.com/";


    default BNCTokenDTO.RequestGetTokenDTO defaultTokenDto() {
        return BNCTokenDTO.RequestGetTokenDTO.builder()
                .clientId(ClientConstants.CLIENT_ID)
                .audience(ClientConstants.AUDIENCE)
                .grantType(ClientConstants.GRANT_TYPE)
                .build();
    }

    BNCTokenDTO.ResponseGetTokenDTO getToken(BNCTokenDTO.RequestGetTokenDTO requestGetTokenDTO);

    AssetDTO.AssetResponseDTO getToAssetById(String assetId);

    MarketDTO.MarketResponseDTO getToMarketById(String marketId);

    ContentGenericWrapper<MarketDTO.MarketResponseDTO> getToMarket(MarketDTO request);

    ContentGenericWrapper<AssetDTO.AssetResponseDTO> getToAsset(AssetDTO.AssetRequestDTO request);

    ContentGenericWrapper<AssetTickerResponse> getToAssetTicker(String assetId, boolean withPercentChange);


}

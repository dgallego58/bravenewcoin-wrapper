package co.com.bancolombia.mscurrencytest.infrastructure.client;

import co.com.bancolombia.mscurrencytest.infrastructure.client.constants.ClientConstants;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

public interface BNCService {

    String baseUrl = "https://bravenewcoin.p.rapidapi.com/";

    default HttpHeaders defaultHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add(ClientConstants.RAPID_API_KEY_HEADER_NAME, ClientConstants.RAPID_API_KEY_HEADER_VALUE);
        httpHeaders.add(ClientConstants.RAPID_API_HOST_HEADER_NAME, ClientConstants.RAPID_API_HOST_HEADER_VALUE);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

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

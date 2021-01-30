package co.com.bancolombia.mscurrencytest.infrastructure.client;

import co.com.bancolombia.mscurrencytest.infrastructure.client.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class BraveNewCoinClient implements BNCService {

    private final RestTemplate restTemplate;

    @Override
    public BNCTokenDTO.ResponseGetTokenDTO getToken(BNCTokenDTO.RequestGetTokenDTO requestGetTokenDTO) {


        RequestEntity<BNCTokenDTO.RequestGetTokenDTO> requestToken = RequestEntity.post(URI.create(BNC_URL + "oauth/token"))
                .body(requestGetTokenDTO);

        ResponseEntity<BNCTokenDTO.ResponseGetTokenDTO> response = restTemplate.exchange(requestToken, BNCTokenDTO.ResponseGetTokenDTO.class);
        return response.getBody();
    }

    @Override
    public AssetDTO.AssetResponseDTO getToAssetById(String assetId) {
        String uri = BNC_URL.concat(String.format("%s/%s", LookUp.ASSET.getValue(), assetId));
        RequestEntity<Void> request = RequestEntity.get(URI.create(uri)).build();
        ParameterizedTypeReference<AssetDTO.AssetResponseDTO> typeReference = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<AssetDTO.AssetResponseDTO> response = restTemplate.exchange(request, typeReference);

        return response.getBody();
    }

    @Override
    public MarketDTO.MarketResponseDTO getToMarketById(String marketId) {
        String uri = BNC_URL.concat(String.format("%s/%s", LookUp.MARKET.getValue(), marketId));
        RequestEntity<Void> request = RequestEntity.get(URI.create(uri)).build();
        ParameterizedTypeReference<MarketDTO.MarketResponseDTO> typeReference = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<MarketDTO.MarketResponseDTO> response = restTemplate.exchange(request, typeReference);

        return response.getBody();
    }

    @Override
    public ContentGenericWrapper<MarketDTO.MarketResponseDTO> getToMarket(MarketDTO request) {
        String uri = BNC_URL.concat(String.format("%s", LookUp.MARKET.getValue()));
        UriComponentsBuilder uriComponents = UriComponentsBuilder.fromHttpUrl(uri);
        if (request.getBaseAssetId() != null && !request.getBaseAssetId().isBlank()) {
            uriComponents.queryParam("baseAssetId", request.getBaseAssetId());
        }
        if (request.getQuoteAssetId() != null && !request.getQuoteAssetId().isBlank()) {
            uriComponents.queryParam("quoteAssetId", request.getQuoteAssetId());
        }
        RequestEntity<Void> requestEntity = RequestEntity.get(uriComponents.build().toUri()).build();
        ParameterizedTypeReference<ContentGenericWrapper<MarketDTO.MarketResponseDTO>> typeReference = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<ContentGenericWrapper<MarketDTO.MarketResponseDTO>> response = restTemplate.exchange(requestEntity, typeReference);
        return response.getBody();
    }

    @Override
    public ContentGenericWrapper<AssetDTO.AssetResponseDTO> getToAsset(AssetDTO.AssetRequestDTO request) {
        String uri = BNC_URL.concat(String.format("%s", LookUp.ASSET.getValue()));

        UriComponentsBuilder uriComponents = UriComponentsBuilder.fromHttpUrl(uri);

        if (!request.getSymbol().isEmpty()) {
            uriComponents.queryParam("symbol", request.getSymbol());
        }
        if (request.getStatus() != null) {
            uriComponents.queryParam("status", request.getStatus().getValue());
        }
        if (request.getType() != null && !request.getType().isEmpty()) {
            uriComponents.queryParam("type", request.getType());
        }
        ParameterizedTypeReference<ContentGenericWrapper<AssetDTO.AssetResponseDTO>> typeReference = new ParameterizedTypeReference<>() {
        };
        RequestEntity<Void> requestEntity = RequestEntity.get(uriComponents.build().toUri()).build();

        ResponseEntity<ContentGenericWrapper<AssetDTO.AssetResponseDTO>> response = restTemplate.exchange(requestEntity, typeReference);
        return response.getBody();
    }

    @Override
    public ContentGenericWrapper<AssetTickerResponse> getToAssetTicker(String assetId, boolean withPercentChange) {
        String uri = BNC_URL.concat(String.format("%s", "market-cap"));
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("assetId", assetId)
                .queryParam("percentChange", withPercentChange);

        ParameterizedTypeReference<ContentGenericWrapper<AssetTickerResponse>> typeReference = new ParameterizedTypeReference<>() {
        };
        RequestEntity<Void> request = RequestEntity.get(uriBuilder.build().toUri())
                .headers(h -> h.setBearerAuth(getToken(defaultTokenDto()).getAccessToken()))
                .build();
        ResponseEntity<ContentGenericWrapper<AssetTickerResponse>> response = restTemplate.exchange(request, typeReference);

        return response.getBody();
    }
}

package co.com.bancolombia.mscurrencytest.infrastructure.client.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true, builderClassName = "ContentAssetTickerBuilder")
@JsonDeserialize(builder = ContentAssetTickerResponse.ContentAssetTickerResponseBuilder.class)
public class ContentAssetTickerResponse {

    private String id;
    private String assetId;
    private LocalDateTime timestamp;
    private int marketCapRank;
    private int volumeRank;
    private BigDecimal price;
    private BigDecimal volume;
    private Long totalSupply;
    private Long freeFloatSupply;
    private BigDecimal marketCap;
    private BigDecimal totalMarketCap;
    private MarketCapPC marketCapPercentChange;
    private TotalMarketCapPC totalMarketCapPercentChange;
    private VolumePC volumePercentChange;
    private PricePC pricePercentChange;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ContentAssetTickerResponseBuilder {

    }

}

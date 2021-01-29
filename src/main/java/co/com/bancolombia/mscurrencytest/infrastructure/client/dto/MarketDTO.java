package co.com.bancolombia.mscurrencytest.infrastructure.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Data
@Builder(toBuilder = true, builderClassName = "MarketDTOBuilder")
@JsonDeserialize(builder = MarketDTO.MarketDTOBuilder.class)
public class MarketDTO {

    private String baseAssetId;
    private String quoteAssetId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class MarketDTOBuilder {

    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class MarketResponseDTO {
        String baseAssetId;
        String quoteAssetId;
        String id;
    }
}

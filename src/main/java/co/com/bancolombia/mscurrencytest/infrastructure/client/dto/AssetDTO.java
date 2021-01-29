package co.com.bancolombia.mscurrencytest.infrastructure.client.dto;

import co.com.bancolombia.mscurrencytest.domain.model.entities.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

public class AssetDTO {


    public enum AssetStatus {
        @JsonProperty("ACTIVE") ACTIVE("ACTIVE"), @JsonProperty("INACTIVE") INACTIVE("INACTIVE");

        private final String value;

        AssetStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }


    }


    @Data
    @Builder(toBuilder = true, builderClassName = "AssetRequestDTOBuilder")
    @JsonDeserialize(builder = AssetDTO.AssetRequestDTO.AssetRequestDTOBuilder.class)
    public static class AssetRequestDTO {
        private String symbol;
        private AssetStatus status;
        private String type;

        @JsonPOJOBuilder(withPrefix = "")
        public static class AssetRequestDTOBuilder {

        }

    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class AssetResponseDTO {
        String id;
        String name;
        String symbol;
        String status;
        String type;
        String url;

        public Currency mapToEntity() {
            return new Currency().setAssetId(this.id)
                    .setStatus(this.status)
                    .setSymbol(this.symbol)
                    .setType(this.type)
                    .setName(this.name);
        }
    }

}

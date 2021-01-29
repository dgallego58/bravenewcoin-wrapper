package co.com.bancolombia.mscurrencytest.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

public class BNCTokenDTO {

    private BNCTokenDTO() {
        //sonar
    }

    @Data
    @Builder(toBuilder = true, builderClassName = "RequestGetTokenDTOBuilder")
    @JsonDeserialize(builder = BNCTokenDTO.RequestGetTokenDTO.RequestGetTokenDTOBuilder.class)
    public static class RequestGetTokenDTO {

        private String audience;
        @JsonProperty("client_id")
        private String clientId;
        @JsonProperty("grant_type")
        private String grantType;

        @JsonPOJOBuilder(withPrefix = "")
        public static class RequestGetTokenDTOBuilder {

        }

    }

    @Value
    @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class ResponseGetTokenDTO {

        @JsonProperty("access_token")
        String accessToken;
        String scope;
        @JsonProperty("expires_in")
        int expiresIn;
        @JsonProperty("token_type")
        String tokenType;


    }


}

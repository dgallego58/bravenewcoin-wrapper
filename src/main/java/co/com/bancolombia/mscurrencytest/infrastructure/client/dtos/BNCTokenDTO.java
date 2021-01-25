package co.com.bancolombia.mscurrencytest.infrastructure.client.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

public class BNCTokenDTO {


    @Data
    @Builder(toBuilder = true)
    public static class RequestGetTokenDTO {

        private String audience;
        @JsonProperty("client_id")
        private String clientId;
        @JsonProperty("grant_type")
        private String grantType;

    }

    @Data
    @Builder(toBuilder = true)
    public static class ResponseGetTokenDTO {

        @JsonProperty("access_token")
        private String accessToken;
        private String scope;
        @JsonProperty("expires_in")
        private int expiresIn;
        @JsonProperty("token_type")
        private String tokenType;

    }


}

package co.com.bancolombia.mscurrencytest.infrastructure.client.dtos;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class AssetTickerDTO {

    @NotBlank(message = "assetId must not be null or empty") String assetId;
    boolean percentChange;


}

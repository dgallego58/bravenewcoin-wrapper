package co.com.bancolombia.mscurrencytest.infrastructure.client.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AssetTickerResponse {

    String id;
    String assetId;
    LocalDateTime timestamp;
    int marketCapRank;
    int volumeRank;
    BigDecimal price;
    BigDecimal volume;
    Long totalSupply;
    Long freeFloatSupply;
    BigDecimal marketCap;
    BigDecimal totalMarketCap;
    //the below targets are null when the request is marked as false to getToAssetTicket
    MarketCapPC marketCapPercentChange;
    TotalMarketCapPC totalMarketCapPercentChange;
    VolumePC volumePercentChange;
    PricePC pricePercentChange;


}

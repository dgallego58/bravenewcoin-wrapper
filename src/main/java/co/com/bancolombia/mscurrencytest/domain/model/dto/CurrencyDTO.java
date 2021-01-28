package co.com.bancolombia.mscurrencytest.domain.model.dto;

import co.com.bancolombia.mscurrencytest.domain.model.entities.Currency;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true, builderClassName = "CurrencyDTOBuilder")
@JsonDeserialize(builder = CurrencyDTO.CurrencyDTOBuilder.class)
public class CurrencyDTO {

    private String assetId;
    private String symbol;
    private String type;
    private BigDecimal price;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CurrencyDTOBuilder {
        //lombok serializer
    }

    public static CurrencyDTO mapToDto(Currency currency) {
        return CurrencyDTO.builder()
                .assetId(currency.getApiIdentifier())
                .symbol(currency.getSymbol())
                .type(currency.getType())
                .price(currency.getPrice())
                .build();
    }

}

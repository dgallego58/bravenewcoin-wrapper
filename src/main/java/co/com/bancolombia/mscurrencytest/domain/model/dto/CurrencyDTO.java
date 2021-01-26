package co.com.bancolombia.mscurrencytest.domain.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true, builderClassName = "CurrencyDTOBuilder")
@JsonDeserialize(builder = CurrencyDTO.CurrencyDTOBuilder.class)
public class CurrencyDTO {

    private String coinName;
    private String symbol;
    private String type;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CurrencyDTOBuilder {
        //lombok serializer
    }
}

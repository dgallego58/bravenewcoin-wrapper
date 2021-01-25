package co.com.bancolombia.mscurrencytest.domain.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CurrencyDTO {

    private String coinName;
    private String symbol;
    private String type;
}

package co.com.bancolombia.mscurrencytest.domain.model.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserDTO {

    private String username;
    private transient String password;
    private String firstname;
    private String lastname;
    private CurrencyDTO currencyDTO;

}

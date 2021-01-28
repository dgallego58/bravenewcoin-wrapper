package co.com.bancolombia.mscurrencytest.domain.model.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder(toBuilder = true, builderClassName = "UserDTOBuilder")
@JsonDeserialize(builder = UserDTO.UserDTOBuilder.class)
public class UserDTO {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    @NotBlank(message = "This is the currency abbr3 name, must be COP, USD, BTC...")
    private String favoriteCurrencySymbol;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserDTOBuilder {
        //lombok and jackson serialization
    }

}

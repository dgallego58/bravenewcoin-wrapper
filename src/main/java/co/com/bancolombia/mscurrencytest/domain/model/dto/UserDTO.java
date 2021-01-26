package co.com.bancolombia.mscurrencytest.domain.model.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true, builderClassName = "UserDTOBuilder")
@JsonDeserialize(builder = UserDTO.UserDTOBuilder.class)
public class UserDTO {

    private String username;
    private String password;
    private String firstname;
    private String lastname;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserDTOBuilder {
        //lombok and jackson serialization
    }

}

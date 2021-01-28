package co.com.bancolombia.mscurrencytest.infrastructure.config.security.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
public class LoginDTO {

    String username;
    String password;

}

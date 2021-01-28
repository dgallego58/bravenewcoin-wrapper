package co.com.bancolombia.mscurrencytest.infrastructure.config.security.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Date;

@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
public class LoginResponseDTO {

  String accessToken;
  Date expirationTime;
  String type;


}

package co.com.bancolombia.mscurrencytest.infrastructure.config.security;

import co.com.bancolombia.mscurrencytest.infrastructure.config.security.auth.LoginDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.config.security.auth.LoginResponseDTO;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {

    LoginResponseDTO authenticate(LoginDTO requestLogin) throws AuthenticationException;

}

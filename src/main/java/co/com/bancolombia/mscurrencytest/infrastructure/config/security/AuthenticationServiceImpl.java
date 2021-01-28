package co.com.bancolombia.mscurrencytest.infrastructure.config.security;

import co.com.bancolombia.mscurrencytest.infrastructure.config.security.auth.LoginDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.config.security.auth.LoginResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static co.com.bancolombia.mscurrencytest.infrastructure.config.security.SecurityConstants.EXPIRATION_TIME;
import static co.com.bancolombia.mscurrencytest.infrastructure.config.security.SecurityConstants.SECRET;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginResponseDTO authenticate(LoginDTO requestLogin) throws BadCredentialsException {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestLogin
                .getUsername(), requestLogin.getPassword()));
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        Date expirationTime = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expirationTime)
                .sign(Algorithm.HMAC512(SECRET.getBytes(StandardCharsets.UTF_8)));

        return new LoginResponseDTO(token, expirationTime, "Bearer");

    }
}

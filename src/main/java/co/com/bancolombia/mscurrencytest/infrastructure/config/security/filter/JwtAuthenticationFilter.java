package co.com.bancolombia.mscurrencytest.infrastructure.config.security.filter;

import co.com.bancolombia.mscurrencytest.domain.model.entities.User;
import co.com.bancolombia.mscurrencytest.infrastructure.service.LoginUserDetails;
import co.com.bancolombia.mscurrencytest.infrastructure.utils.Converter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import static co.com.bancolombia.mscurrencytest.infrastructure.config.security.SecurityConstants.EXPIRATION_TIME;
import static co.com.bancolombia.mscurrencytest.infrastructure.config.security.SecurityConstants.SECRET;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/user/login");

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User credentials = Converter.configuredObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials
                    .getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String token = JWT.create()
                .withSubject(((LoginUserDetails) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes(StandardCharsets.UTF_8)));
        String body = ((LoginUserDetails) authResult.getPrincipal()).getUsername() + " " + token;

        response.getWriter().write(body);
        response.getWriter().flush();
    }
}

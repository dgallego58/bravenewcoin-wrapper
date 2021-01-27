package co.com.bancolombia.mscurrencytest.infrastructure.config.security.filter;

import co.com.bancolombia.mscurrencytest.domain.model.entities.User;
import co.com.bancolombia.mscurrencytest.infrastructure.config.security.UserDetailsImpl;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            String username = credentials.getUsername();
            String password = credentials.getPassword();
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, List.of()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        Date expirationTime = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String token = JWT.create()
                .withSubject(((UserDetailsImpl) authResult.getPrincipal()).getUsername())
                .withExpiresAt(expirationTime)
                .sign(Algorithm.HMAC512(SECRET.getBytes(StandardCharsets.UTF_8)));

        Map<String, Object> bodyResp = new HashMap<>();
        bodyResp.put("accessToken", token);
        bodyResp.put("expirationTime", expirationTime);
        bodyResp.put("type", "Bearer");
        response.addHeader("Content-type", "application/json");
        response.getWriter().write(Converter.configuredObjectMapper().writeValueAsString(bodyResp));
        response.getWriter().flush();
    }
}

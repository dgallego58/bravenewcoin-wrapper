package co.com.bancolombia.mscurrencytest.infrastructure.controller;

import co.com.bancolombia.mscurrencytest.domain.exception.CurrencyNotFound;
import co.com.bancolombia.mscurrencytest.domain.model.dto.UserDTO;
import co.com.bancolombia.mscurrencytest.domain.model.gateway.UserRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.config.security.AuthenticationService;
import co.com.bancolombia.mscurrencytest.infrastructure.config.security.auth.LoginDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.config.security.auth.LoginResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Register and Authentication")
@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @PostMapping(path = "/signUp")
    public ResponseEntity<Void> registerUser(@RequestBody UserDTO userDTO) throws CurrencyNotFound {
        userRepository.saveUser(userDTO);
        return ResponseEntity.accepted().build();
    }

    @PostMapping(path = "/login", consumes = "application/json")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO requestBody) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(requestBody));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }


}

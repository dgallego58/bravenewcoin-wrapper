package co.com.bancolombia.mscurrencytest.infrastructure.controller;

import co.com.bancolombia.mscurrencytest.domain.model.dto.UserDTO;
import co.com.bancolombia.mscurrencytest.domain.model.gateway.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping(path = "/signUp")
    public ResponseEntity<Void> registerUser(@RequestBody UserDTO userDTO) {
        userRepository.saveUser(userDTO);
        return ResponseEntity.accepted().build();
    }



}

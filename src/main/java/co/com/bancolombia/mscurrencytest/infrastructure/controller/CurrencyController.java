package co.com.bancolombia.mscurrencytest.infrastructure.controller;

import co.com.bancolombia.mscurrencytest.infrastructure.config.security.utils.SessionJwt;
import co.com.bancolombia.mscurrencytest.infrastructure.service.IUserGeneralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final IUserGeneralService iUserGeneralService;


    @GetMapping(path = "/salute", consumes = "application/json")
    public ResponseEntity<String> sayHello(@RequestParam String sayHello) {

        String usrName = SessionJwt.loggedUsername();
        UserDetails user = iUserGeneralService.loadUserByUsername(usrName);
        return ResponseEntity.ok(user.getUsername() + " SALUDO : " + sayHello);

    }

}

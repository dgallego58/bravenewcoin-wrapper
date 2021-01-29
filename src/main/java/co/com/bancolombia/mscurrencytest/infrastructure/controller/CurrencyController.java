package co.com.bancolombia.mscurrencytest.infrastructure.controller;

import co.com.bancolombia.mscurrencytest.domain.exception.CurrencyNotFound;
import co.com.bancolombia.mscurrencytest.domain.model.dto.CurrencyDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/currency")
@RequiredArgsConstructor
public class CurrencyController {


    private final CurrencyService currencyService;

    @GetMapping(path = "/getCoins", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CurrencyDTO>> getCoins() {
        List<CurrencyDTO> response = currencyService.getUserCurrencies();
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/addCoin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addCurrency(@RequestBody @Valid CurrencyDTO currencyDTO) throws CurrencyNotFound {

        currencyService.addCurrency(currencyDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/addCoin")
                .buildAndExpand("/currency/user")
                .toUri();
        return ResponseEntity.created(location).build();


    }

    @GetMapping(path = "/getTop3")
    public ResponseEntity<List<CurrencyDTO>> getTop3(@RequestParam boolean reversed) {
        return ResponseEntity.ok(currencyService.getTop3(reversed));
    }

}

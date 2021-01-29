package co.com.bancolombia.mscurrencytest.infrastructure.config.error;

import co.com.bancolombia.mscurrencytest.domain.exception.CurrencyNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestErrorHandler {


    @ExceptionHandler(CurrencyNotFound.class)
    public ResponseEntity<Map<String, String>> onCurrencyNotFound(CurrencyNotFound ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

}

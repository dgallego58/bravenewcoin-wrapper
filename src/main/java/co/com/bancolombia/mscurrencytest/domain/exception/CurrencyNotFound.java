package co.com.bancolombia.mscurrencytest.domain.exception;

public class CurrencyNotFound extends Exception {

    public CurrencyNotFound(String message) {
        super(message);
    }

    public CurrencyNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyNotFound(Throwable cause) {
        super(cause);
    }

    public CurrencyNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

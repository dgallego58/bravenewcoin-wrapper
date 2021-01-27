package co.com.bancolombia.mscurrencytest.infrastructure.config.security;

public final class SecurityConstants {

    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 900_000; // 15 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "authorization";
    public static final String SIGN_UP_URL = "/user/signUp";

    private SecurityConstants() {
        //Object constant pool
    }
}


package co.com.bancolombia.mscurrencytest.infrastructure.config.security.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SessionJwt {

    private SessionJwt() {
        //sonar
    }

    public static String loggedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}

package co.com.bancolombia.mscurrencytest.infrastructure.config.security.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SessionJwt {

    public static String loggedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}

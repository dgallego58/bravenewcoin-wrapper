package co.com.bancolombia.mscurrencytest.infrastructure.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class Converter {

    public static ObjectMapper configuredObjectMapper() {
        return new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .registerModules(new JavaTimeModule(), new Jdk8Module());
    }


}

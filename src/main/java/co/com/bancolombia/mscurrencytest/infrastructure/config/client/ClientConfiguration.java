package co.com.bancolombia.mscurrencytest.infrastructure.config.client;

import co.com.bancolombia.mscurrencytest.infrastructure.utils.Converter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;

@Configuration
public class ClientConfiguration {

    private final ResponseErrorHandler responseErrorHandler;

    public ClientConfiguration(ResponseErrorHandler responseErrorHandler) {
        this.responseErrorHandler = responseErrorHandler;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().errorHandler(responseErrorHandler)
                .setConnectTimeout(Duration.ofSeconds(15))
                .setReadTimeout(Duration.ofSeconds(15))
                .additionalInterceptors(new RestClientInterceptor())
                .messageConverters(List.of(new MappingJackson2HttpMessageConverter(Converter.configuredObjectMapper())))
                .build();
    }


}

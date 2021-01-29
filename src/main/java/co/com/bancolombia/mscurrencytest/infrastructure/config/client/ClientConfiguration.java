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
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(List.of(new MappingJackson2HttpMessageConverter(Converter.configuredObjectMapper())));

        RestTemplateBuilder builder = new RestTemplateBuilder();
        builder.errorHandler(responseErrorHandler);
        builder.configure(restTemplate);
        builder.setConnectTimeout(Duration.ofSeconds(15));
        builder.setReadTimeout(Duration.ofSeconds(15));
        return builder.build();
    }


}

package co.com.bancolombia.mscurrencytest.infrastructure.config.client;

import co.com.bancolombia.mscurrencytest.infrastructure.client.constants.ClientConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;


public class RestClientInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        request.getHeaders().set(ClientConstants.RAPID_API_KEY_HEADER_NAME, ClientConstants.RAPID_API_KEY_HEADER_VALUE);
        request.getHeaders()
                .set(ClientConstants.RAPID_API_HOST_HEADER_NAME, ClientConstants.RAPID_API_HOST_HEADER_VALUE);
        request.getHeaders().set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        return execution.execute(request, body);
    }
}

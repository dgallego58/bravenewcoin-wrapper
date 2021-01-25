package co.com.bancolombia.mscurrencytest.infrastructure.client;

import co.com.bancolombia.mscurrencytest.infrastructure.client.constants.ClientConstants;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.BNCTokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BraveNewCoinClient implements BNCService {


    private final RestTemplate restTemplate;


    @Override
    public BNCTokenDTO.ResponseGetTokenDTO getToken(BNCTokenDTO.RequestGetTokenDTO requestGetTokenDTO) {


        RequestEntity<BNCTokenDTO.RequestGetTokenDTO> requestToken = RequestEntity.post(URI.create(baseUrl + "oauth/token"))
                .headers(httpHeaders -> {
                    httpHeaders.add(ClientConstants.RAPID_API_KEY_HEADER_NAME, ClientConstants.RAPID_API_KEY_HEADER_VALUE);
                    httpHeaders.add(ClientConstants.RAPID_API_HOST_HEADER_NAME, ClientConstants.RAPID_API_HOST_HEADER_VALUE);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
                })
                .body(requestGetTokenDTO);
        ResponseEntity<BNCTokenDTO.ResponseGetTokenDTO> response = restTemplate.exchange(requestToken, BNCTokenDTO.ResponseGetTokenDTO.class);

        return response.getBody();
    }
}

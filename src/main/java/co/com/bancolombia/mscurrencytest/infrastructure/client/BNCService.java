package co.com.bancolombia.mscurrencytest.infrastructure.client;

import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.BNCTokenDTO;

public interface BNCService {

    String baseUrl = "https://bravenewcoin.p.rapidapi.com/";

    BNCTokenDTO.ResponseGetTokenDTO getToken(BNCTokenDTO.RequestGetTokenDTO requestGetTokenDTO);



}

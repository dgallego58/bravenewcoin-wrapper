package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.model.gateway.CurrencyRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.AssetDTO;

import java.util.List;

public interface CurrencyService extends CurrencyRepository {

    List<AssetDTO.AssetResponseDTO> getCoinsFromApi(AssetDTO.AssetRequestDTO request);

}

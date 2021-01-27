package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.infrastructure.client.BNCService;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.AssetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final BNCService bncService;

    @Override
    public List<AssetDTO.AssetResponseDTO> getCoinsFromApi(AssetDTO.AssetRequestDTO request) {
        return bncService.getToAsset(request).getContent();
    }
}

package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.model.dto.CurrencyDTO;
import co.com.bancolombia.mscurrencytest.domain.model.entities.Currency;
import co.com.bancolombia.mscurrencytest.domain.model.entities.User;
import co.com.bancolombia.mscurrencytest.domain.model.entities.UserCurrency;
import co.com.bancolombia.mscurrencytest.domain.model.entities.composites.UserCurrencyId;
import co.com.bancolombia.mscurrencytest.infrastructure.client.BNCService;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.AssetDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.AssetTickerResponse;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.ContentGenericWrapper;
import co.com.bancolombia.mscurrencytest.infrastructure.config.security.utils.SessionJwt;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaCurrencyRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserCurrencyRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {


    private final JpaCurrencyRepository jpaCurrencyRepository;
    private final JpaUserCurrencyRepository jpaUserCurrencyRepository;
    private final JpaUserRepository jpaUserRepository;

    private final BNCService bncService;

    @Override
    @Transactional(readOnly = true)
    public List<CurrencyDTO> getUserCurrencies() {
        List<Integer> userCurrencies = jpaUserCurrencyRepository.findByUserUsername(SessionJwt.loggedUsername())
                .stream()
                .map(UserCurrency::getUserCurrencyId)
                .map(UserCurrencyId::getUserId)
                .collect(Collectors.toList());
        return jpaCurrencyRepository.findByIdIn(userCurrencies)
                .stream()
                .map(CurrencyDTO::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CurrencyDTO> getTop3(Sort sort) {

  /*      List<Integer> userCurrencies = jpaUserCurrencyRepository.findCurrenciesIdsByUsername(SessionJwt.loggedUsername())
                .stream()
                .map(uc -> uc.getCurrency().getId())
                .collect(Collectors.toList());
        return jpaCurrencyRepository.findAllById(userCurrencies)
                .stream()
                .map(currency -> CurrencyDTO.builder()
                        .assetId(currency.getApiIdentifier())
                        .symbol(currency.getSymbol())
                        .type(currency.getType())
                        .price(currency.getPrice())
                        .build())
                .collect(Collectors.toList());*/
        return null;
    }

    @Override
    @Transactional
    public void addCurrency(CurrencyDTO currencyDTO) {
        String username = SessionJwt.loggedUsername();

        Currency currency = jpaCurrencyRepository.findByApiIdentifier(currencyDTO.getAssetId()).orElseGet(() -> {
            ContentGenericWrapper<AssetTickerResponse> response = bncService.getToAssetTicker(currencyDTO.getAssetId(), false);
            BigDecimal price = response.getContent()
                    .stream()
                    .findFirst()
                    .map(AssetTickerResponse::getPrice)
                    .orElse(BigDecimal.ZERO);
            AssetDTO.AssetResponseDTO assetFromApi = bncService.getToAssetById(currencyDTO.getAssetId());
            return new Currency().setType(assetFromApi.getType())
                    .setStatus(assetFromApi.getStatus())
                    .setSymbol(assetFromApi.getSymbol())
                    .setName(assetFromApi.getName())
                    .setPrice(price)
                    .setApiIdentifier(assetFromApi.getId());
        });
        Optional<User> user = jpaUserRepository.findByUsername(username);
        user.ifPresent(value -> value.addCurrency(currency, false));
    }
}


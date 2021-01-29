package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.exception.CurrencyNotFound;
import co.com.bancolombia.mscurrencytest.domain.model.dto.CurrencyDTO;
import co.com.bancolombia.mscurrencytest.domain.model.entities.Currency;
import co.com.bancolombia.mscurrencytest.domain.model.entities.User;
import co.com.bancolombia.mscurrencytest.domain.model.entities.UserCurrency;
import co.com.bancolombia.mscurrencytest.infrastructure.client.BNCService;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dto.AssetDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dto.AssetTickerResponse;
import co.com.bancolombia.mscurrencytest.infrastructure.config.security.utils.SessionJwt;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaCurrencyRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserCurrencyRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
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
        return jpaUserCurrencyRepository.findByUserUsername(SessionJwt.loggedUsername())
                .stream()
                .map(this::currencyConverter)
                .map(CurrencyDTO::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CurrencyDTO> getTop3(boolean reversed) {

        return jpaUserCurrencyRepository.findByUserUsername(SessionJwt.loggedUsername())
                .stream()
                .map(this::currencyConverter)
                .sorted((c1, c2) -> reversed ? c1.getPrice().compareTo(c2.getPrice()) : c2.getPrice()
                        .compareTo(c1.getPrice()))
                .map(CurrencyDTO::mapToDto)
                .collect(Collectors.toList());
    }

    public Currency currencyConverter(UserCurrency userCurrency) {
        Currency c = userCurrency.getCurrency();
        if (userCurrency.isFavorite()) {
            BigDecimal value = c.getPrice().multiply(BigDecimal.valueOf(0.57));
            c.setPrice(value);
        }
        return c;
    }

    @Override
    @Transactional
    public void addCurrency(CurrencyDTO currencyDTO) throws CurrencyNotFound {

        Optional<Currency> currency = jpaCurrencyRepository.findByAssetId(currencyDTO);
        if (currency.isPresent()) {
            addAndStoreCurrencyToUser(currency.get());
            return;
        }
        AssetDTO.AssetResponseDTO responseDTO = bncService.getToAssetById(currencyDTO.getAssetId());
        BigDecimal price = bncService.getToAssetTicker(responseDTO.getId(), false)
                .getContent()
                .stream()
                .map(AssetTickerResponse::getPrice)
                .findFirst()
                .orElse(BigDecimal.ZERO);

        if (responseDTO.getId() == null) {
            throw new CurrencyNotFound("Currency not found");
        }
        Currency currencyFromApi = responseDTO.mapToEntity().setPrice(price);
        addAndStoreCurrencyToUser(currencyFromApi);

    }

    public void addAndStoreCurrencyToUser(Currency currency) {
        Optional<User> usrInDb = jpaUserRepository.findByUsername(SessionJwt.loggedUsername());
        if (usrInDb.isPresent()) {
            User user = usrInDb.get();
            jpaCurrencyRepository.save(currency);
            user.addCurrency(currency, false);
            jpaUserRepository.save(user);
        }
    }

}


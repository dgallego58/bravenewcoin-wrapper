package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.exception.CurrencyNotFound;
import co.com.bancolombia.mscurrencytest.domain.model.dto.CurrencyDTO;
import co.com.bancolombia.mscurrencytest.domain.model.dto.UserDTO;
import co.com.bancolombia.mscurrencytest.domain.model.entities.Currency;
import co.com.bancolombia.mscurrencytest.domain.model.entities.User;
import co.com.bancolombia.mscurrencytest.infrastructure.client.BNCService;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dto.AssetDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dto.AssetTickerResponse;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dto.ContentGenericWrapper;
import co.com.bancolombia.mscurrencytest.infrastructure.config.security.UserDetailsImpl;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaCurrencyRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserGeneralService implements IUserGeneralService {

    private final JpaUserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final BNCService bncService;
    private final JpaCurrencyRepository jpaCurrencyRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(UserDTO userDTO) throws CurrencyNotFound {
        CurrencyDTO currencyDTO = CurrencyDTO.builder().symbol(userDTO.getFavoriteCurrencySymbol()).build();
        Optional<Currency> currencyOpt = jpaCurrencyRepository.findByCurrencyDto(currencyDTO);
        Currency currency;
        if (currencyOpt.isPresent()) {
            currency = currencyOpt.get();
        } else {
            currency = retrieveCurrencyFromApi(userDTO.getFavoriteCurrencySymbol());
        }

        User user = new User().setUsername(userDTO.getUsername())
                .setFirstname(userDTO.getFirstname())
                .setLastname(userDTO.getLastname())
                .setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.addCurrency(currency, true);
        jpaUserRepository.save(user);
    }

    public Currency retrieveCurrencyFromApi(String symbol) throws CurrencyNotFound {
        ContentGenericWrapper<AssetDTO.AssetResponseDTO> assets = bncService.getToAsset(AssetDTO.AssetRequestDTO.builder()
                .symbol(symbol)
                .build());
        Currency currency = assets.getContent()
                .stream()
                .findFirst()
                .map(AssetDTO.AssetResponseDTO::mapToEntity)
                .orElseThrow(() -> new CurrencyNotFound("Coin not found"));
        ContentGenericWrapper<AssetTickerResponse> assetTickerResponse = bncService.getToAssetTicker(currency.getAssetId(), true);
        BigDecimal price = assetTickerResponse.getContent()
                .stream()
                .findFirst()
                .map(AssetTickerResponse::getPrice)
                .orElse(BigDecimal.ZERO);
        currency.setPrice(price);
        return currency;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find user"));
    }


}

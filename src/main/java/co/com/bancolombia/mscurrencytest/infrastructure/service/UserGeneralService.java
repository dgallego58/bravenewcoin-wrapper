package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.exception.CurrencyNotFound;
import co.com.bancolombia.mscurrencytest.domain.model.dto.UserDTO;
import co.com.bancolombia.mscurrencytest.domain.model.entities.Currency;
import co.com.bancolombia.mscurrencytest.domain.model.entities.User;
import co.com.bancolombia.mscurrencytest.infrastructure.client.BNCService;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.AssetDTO;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.AssetTickerResponse;
import co.com.bancolombia.mscurrencytest.infrastructure.client.dtos.ContentGenericWrapper;
import co.com.bancolombia.mscurrencytest.infrastructure.config.security.UserDetailsImpl;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserGeneralService implements IUserGeneralService {

    private final JpaUserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final BNCService bncService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(UserDTO userDTO) throws CurrencyNotFound {
        ContentGenericWrapper<AssetDTO.AssetResponseDTO> assets = bncService.getToAsset(AssetDTO.AssetRequestDTO.builder()
                .symbol(userDTO.getFavoriteCurrencySymbol())
                .build());
        AssetDTO.AssetResponseDTO assetResponseDTO = assets.getContent()
                .stream()
                .findFirst()
                .orElseThrow(() -> new CurrencyNotFound("Coin not found"));
        ContentGenericWrapper<AssetTickerResponse> assetTickerResponse = bncService.getToAssetTicker(assetResponseDTO.getId(), true);
        BigDecimal price = assetTickerResponse.getContent()
                .stream()
                .findFirst()
                .map(AssetTickerResponse::getPrice)
                .orElse(BigDecimal.ZERO);

        Currency currency = new Currency().setName(assetResponseDTO.getName())
                .setType(assetResponseDTO.getType())
                .setPrice(price)
                .setSymbol(assetResponseDTO.getSymbol())
                .setStatus(assetResponseDTO.getStatus())
                .setApiIdentifier(assetResponseDTO.getId());

        User user = new User().setUsername(userDTO.getUsername())
                .setActive(true)
                .setFirstname(userDTO.getFirstname())
                .setLastname(userDTO.getLastname())
                .setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.addCurrency(currency, true);
        jpaUserRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return jpaUserRepository.findByUsername(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find user"));
    }




}

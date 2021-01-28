package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.model.dto.CurrencyDTO;
import co.com.bancolombia.mscurrencytest.domain.model.gateway.CurrencyRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CurrencyService extends CurrencyRepository {

    List<CurrencyDTO> getUserCurrencies();

    List<CurrencyDTO> getTop3(Sort sort);

    void addCurrency(CurrencyDTO currencyDTO);

}

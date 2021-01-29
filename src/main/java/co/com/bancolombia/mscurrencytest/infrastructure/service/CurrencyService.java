package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.exception.CurrencyNotFound;
import co.com.bancolombia.mscurrencytest.domain.model.dto.CurrencyDTO;
import co.com.bancolombia.mscurrencytest.domain.model.gateway.CurrencyRepository;

import java.util.List;

public interface CurrencyService extends CurrencyRepository {

    List<CurrencyDTO> getUserCurrencies();

    List<CurrencyDTO> getTop3(boolean reversed);

    void addCurrency(CurrencyDTO currencyDTO) throws CurrencyNotFound;

}

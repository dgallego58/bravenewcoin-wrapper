package co.com.bancolombia.mscurrencytest.domain.model.gateway;

import co.com.bancolombia.mscurrencytest.domain.model.entities.Currency;

import java.util.List;

public interface CurrencyRepository {

    void saveAll(List<Currency> currencies);
    void findByCurrencyName(String name);
    void findBySymbol(String symbol);

}

package co.com.bancolombia.mscurrencytest.infrastructure.repository;

import co.com.bancolombia.mscurrencytest.domain.model.dto.CurrencyDTO;
import co.com.bancolombia.mscurrencytest.domain.model.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCurrencyRepository extends JpaRepository<Currency, Integer> {


    @Query("select distinct c from Currency c where" + " c.name like %:#{#currencyDTO.getName()}% or" + " c.symbol like :#{#currencyDTO.getSymbol()} or" + " c.type like :#{#currencyDTO.getType()} or " + " c.assetId like :#{#currencyDTO.getAssetId()}")
    Optional<Currency> findByCurrencyDto(@Param("currencyDTO") CurrencyDTO currencyDTO);


}

package co.com.bancolombia.mscurrencytest.infrastructure.repository;

import co.com.bancolombia.mscurrencytest.domain.model.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaCurrencyRepository extends JpaRepository<Currency, Integer> {

    Optional<Currency> findByApiIdentifier(String apiIdentifier);

    List<Currency> findByIdIn(List<Integer> ids);

}

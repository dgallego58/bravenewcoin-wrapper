package co.com.bancolombia.mscurrencytest.infrastructure.repository;

import co.com.bancolombia.mscurrencytest.domain.model.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCurrencyRepository extends JpaRepository<Currency, Integer> {
}

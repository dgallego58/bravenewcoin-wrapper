package co.com.bancolombia.mscurrencytest.infrastructure.repository;

import co.com.bancolombia.mscurrencytest.domain.model.entities.UserCurrency;
import co.com.bancolombia.mscurrencytest.domain.model.entities.composites.UserCurrencyId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaUserCurrencyRepository extends JpaRepository<UserCurrency, UserCurrencyId> {

    @EntityGraph(attributePaths = "currency")
    List<UserCurrency> findByUserUsername(@Param("username") String username);


}

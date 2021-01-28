package co.com.bancolombia.mscurrencytest.infrastructure.repository;

import co.com.bancolombia.mscurrencytest.domain.model.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(attributePaths = "userCurrencies")
    Optional<User> findByUsername(String username);


}

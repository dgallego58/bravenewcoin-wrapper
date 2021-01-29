package co.com.bancolombia.mscurrencytest.infrastructure.integration;

import co.com.bancolombia.mscurrencytest.domain.model.entities.User;
import co.com.bancolombia.mscurrencytest.infrastructure.client.BNCService;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaCurrencyRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserCurrencyRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.service.CurrencyService;
import co.com.bancolombia.mscurrencytest.infrastructure.service.CurrencyServiceImpl;
import co.com.bancolombia.mscurrencytest.mocks.DefaultAnswers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//integration
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase
@Disabled
class CurrencyServiceImplTest extends DefaultAnswers {

    @Autowired
    JpaCurrencyRepository jpaCurrencyRepository;
    @Autowired
    JpaUserCurrencyRepository jpaUserCurrencyRepository;
    @Autowired
    JpaUserRepository jpaUserRepository;
    @Autowired
    BNCService bncService;

    CurrencyService currencyService;

    @BeforeEach
    @Transactional
    void setUp() {
        currencyService = new CurrencyServiceImpl(jpaCurrencyRepository, jpaUserCurrencyRepository, jpaUserRepository, bncService);
        mockSecurityContext();
        saveDb();
    }

    private void saveDb() {

        User user = new User();
        user.setFirstname("TestFirstname").setLastname("TestLastname").setUsername("test").setPassword("testpsw");
        user.addCurrency(mockCurrencies().get(0), true);
        user.addCurrency(mockCurrencies().get(1), false);
        System.out.println("LA COLECCION Está vacía?" + user.getUserCurrencies().isEmpty());
        jpaUserRepository.save(user);
    }

    @Test
    @Transactional(readOnly = true)
    void getUserCurrencies() {
        assertNotNull(currencyService.getUserCurrencies());
        assertFalse(currencyService.getUserCurrencies().isEmpty());
    }


}
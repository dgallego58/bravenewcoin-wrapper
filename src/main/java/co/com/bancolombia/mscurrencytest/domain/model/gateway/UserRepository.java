package co.com.bancolombia.mscurrencytest.domain.model.gateway;

import co.com.bancolombia.mscurrencytest.domain.exception.CurrencyNotFound;
import co.com.bancolombia.mscurrencytest.domain.model.dto.UserDTO;

public interface UserRepository {

    void saveUser(UserDTO user) throws CurrencyNotFound;


}

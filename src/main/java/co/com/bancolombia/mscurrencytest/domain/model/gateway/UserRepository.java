package co.com.bancolombia.mscurrencytest.domain.model.gateway;

import co.com.bancolombia.mscurrencytest.domain.model.dto.UserDTO;
import co.com.bancolombia.mscurrencytest.domain.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void saveUser(UserDTO user);

}

package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.model.gateway.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserGeneralService extends UserRepository, UserDetailsService {
}

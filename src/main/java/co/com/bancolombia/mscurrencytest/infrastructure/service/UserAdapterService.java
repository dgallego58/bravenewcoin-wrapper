package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.model.dto.UserDTO;
import co.com.bancolombia.mscurrencytest.domain.model.entities.User;
import co.com.bancolombia.mscurrencytest.domain.model.gateway.UserRepository;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAdapterService implements UserRepository, UserDetailsService {

    private final JpaUserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(UserDTO userDTO) {
        String passwordEncoded = passwordEncoder.encode(userDTO.getPassword());
        User user = new User().setUsername(userDTO.getUsername())
                .setFirstname(userDTO.getFirstname())
                .setLastname(userDTO.getLastname())
                .setPassword(passwordEncoded);
        jpaUserRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return jpaUserRepository.findByUsername(username)
                .map(LoginUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find user"));
    }
}

package co.com.bancolombia.mscurrencytest.infrastructure.service;

import co.com.bancolombia.mscurrencytest.domain.model.dto.UserDTO;
import co.com.bancolombia.mscurrencytest.domain.model.entities.User;
import co.com.bancolombia.mscurrencytest.infrastructure.config.security.UserDetailsImpl;
import co.com.bancolombia.mscurrencytest.infrastructure.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserGeneralService implements IUserGeneralService {

    private final JpaUserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(UserDTO userDTO) {
        String encodedPass = passwordEncoder.encode(userDTO.getPassword());
        User user = new User().setUsername(userDTO.getUsername()).setActive(true)
                .setFirstname(userDTO.getFirstname())
                .setLastname(userDTO.getLastname())
                .setPassword(encodedPass);
        jpaUserRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return jpaUserRepository.findByUsername(username).map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find user"));
    }


}

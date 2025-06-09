package sk.kuchta.eshop.implementation.serviceImpl.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import sk.kuchta.eshop.api.exception.ResourceNotFoundException;
import sk.kuchta.eshop.implementation.entity.user.UserAccount;
import sk.kuchta.eshop.implementation.repository.user.UserAccountRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserAccountRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserAccount user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}

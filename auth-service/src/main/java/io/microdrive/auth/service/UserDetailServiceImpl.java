package io.microdrive.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import io.microdrive.auth.domain.User;
import io.microdrive.auth.repository.UserRepository;

@Service
@Primary
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOp = userRepository.findByUsername(username);
        return userOp.orElseThrow(() -> new UsernameNotFoundException(("Username not found")));
    }
}

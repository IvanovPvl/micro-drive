package io.microdrive.auth.service;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import io.microdrive.auth.domain.User;
import io.microdrive.auth.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MongoUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> create(User user) {
        this.userRepository.findByUsername(user.getUsername())
                .ifPresent(u -> { throw new IllegalArgumentException("User already exists: " + u.getUsername()); });

        val hash = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        val u = this.userRepository.save(user);

        log.info("New user has been created: {}", user.getUsername());
        return Optional.of(u);
    }
}

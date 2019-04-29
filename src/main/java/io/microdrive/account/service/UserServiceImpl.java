package io.microdrive.account.service;

import io.microdrive.account.domain.User;
import io.microdrive.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        this.repository.findByUsername(user.getUsername())
                .ifPresent(u -> { throw new IllegalArgumentException("User already exists: " + u.getUsername()); });

        val hash = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        val u = this.repository.save(user);

        log.info("New user has been created: {}", user.getUsername());
        return u;
    }
}

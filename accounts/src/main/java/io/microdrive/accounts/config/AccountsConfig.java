package io.microdrive.accounts.config;

import io.microdrive.accounts.domain.Account;
import io.microdrive.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AccountsConfig {

    private final AccountRepository repository;
    private final PasswordEncoder encoder;

    @Bean
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
    public void setup() {
        val user = Account.builder()
                .username("pavel")
                .firstName("Paul")
                .lastName("Stanley")
                .password(encoder.encode("123"))
                .role("user")
                .build();

        if (!repository.findByUsername(user.getUsername()).isPresent()) {
            repository.save(user);
        }
    }

}

package io.microdrive.auth.config;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.UUID;

import io.microdrive.auth.domain.User;
import io.microdrive.auth.repository.UserRepository;

@Slf4j
@Configuration
public class AuthConfig {

    private final UserRepository userRepository;

    public AuthConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
    public void setup() {
        log.info("Setup dummy users");

        val user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("pavel")
                .password("111")
                .role("user")
                .build();

        val driver = User.builder()
                .id(UUID.randomUUID().toString())
                .username("sam")
                .password("111")
                .role("driver")
                .build();

        if (!this.userRepository.findById(user.getId()).isPresent()) {
            this.userRepository.save(user);
        }

        if (!this.userRepository.findById(driver.getId()).isPresent()) {
            this.userRepository.save(driver);
        }
    }
}

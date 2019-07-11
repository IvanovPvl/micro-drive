package io.microdrive.account.config;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import io.microdrive.account.domain.Car;
import io.microdrive.account.repository.CarRepository;

import java.util.UUID;

import io.microdrive.account.domain.User;
import io.microdrive.account.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class AccountConfig {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final PasswordEncoder encoder;

    public AccountConfig(UserRepository userRepository, CarRepository carRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.encoder = encoder;
    }

    @Bean
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
    public void setup() {
        log.info("Setup dummy users");

        val user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("pavel")
                .firstName("Pavel")
                .lastName("Ivanov")
                .password(this.encoder.encode("111"))
                .role("user")
                .build();

        var driver = User.builder()
                .id(UUID.randomUUID().toString())
                .username("sam")
                .firstName("Sam")
                .lastName("Willis")
                .password(this.encoder.encode("111"))
                .role("driver")
                .build();

        val car = Car.builder()
                .mark("Honda")
                .number("A001AA")
                .build();

        if (!this.userRepository.findByUsername(user.getUsername()).isPresent()) {
            this.userRepository.save(user);
        }

        if (!this.userRepository.findByUsername(driver.getUsername()).isPresent()) {
            driver = this.userRepository.save(driver);
            car.setUser(driver);
            this.carRepository.save(car);
        }
    }
}

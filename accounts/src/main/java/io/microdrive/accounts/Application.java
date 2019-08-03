package io.microdrive.accounts;

import io.microdrive.accounts.domain.Account;
import io.microdrive.accounts.domain.Car;
import io.microdrive.accounts.repository.AccountRepository;
import io.microdrive.accounts.repository.CarRepository;
import io.microdrive.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableEurekaClient
@EnableResourceServer
@SpringBootApplication
@RequiredArgsConstructor
@EnableAuthorizationServer
public class Application {

    private final AccountRepository repository;
    private final AccountService accountService;
    private final CarRepository carRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
    public void setup() {
        val user = Account.builder()
                .username("pavel")
                .firstName("Paul")
                .lastName("Stanley")
                .password("123")
                .role("user")
                .build();

        if (!repository.findByUsername(user.getUsername()).isPresent()) {
            accountService.create(user);
        }

        val driver = Account.builder()
                .username("sam")
                .firstName("Sam")
                .lastName("Winston")
                .password("123")
                .role("driver")
                .build();

        var car = Car.builder()
                .mark("Toyota")
                .number("AS123GD")
                .build();

        if (!repository.findByUsername(driver.getUsername()).isPresent()) {
            car = carRepository.save(car);
            driver.setCar(car);
            accountService.create(driver);
        }
    }

}

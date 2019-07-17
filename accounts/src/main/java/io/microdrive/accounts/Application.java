package io.microdrive.accounts;

import io.microdrive.accounts.web.AccountsController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@SpringBootApplication
@RequiredArgsConstructor
@EnableAuthorizationServer
public class Application {

    private final AccountsController apiHandler;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

package io.microdrive.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

//@RefreshScope
@EnableMongoAuditing
@EnableResourceServer
@SpringBootApplication
//@EnableDiscoveryClient
@EnableAuthorizationServer
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}

package io.microdrive.routing;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

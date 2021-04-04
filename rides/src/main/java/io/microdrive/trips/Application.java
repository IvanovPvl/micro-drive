package io.microdrive.trips;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {

    private final ApiHandler apiHandler;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> routes() {
        val user = route().nest(
                accept(APPLICATION_JSON),
                b -> b.POST("/info", apiHandler::info)
                        .POST("/claim", apiHandler::claim)
        ).build();

        val driver = route().nest(
                accept(APPLICATION_JSON),
                b -> b.POST("/start", apiHandler::start)
                        .POST("/finish", apiHandler::finish)
                        .POST("/check", apiHandler::check)
                        .POST("/locations", apiHandler::createLocation)
        ).build();

        return route()
                .path("/user", () -> user)
                .path("/driver", () -> driver)
                .build();
    }

}
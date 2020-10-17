package io.microdrive.drivers.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class Router {
    private final ApiHandler apiHandler;

    @Bean
    RouterFunction<ServerResponse> routes() {
        return route()
            .POST("/request", apiHandler::request)
            .POST("/release", accept(APPLICATION_JSON), apiHandler::release)
            .build();
    }
}

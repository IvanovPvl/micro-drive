package io.microdrive.rides.web;

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
    public RouterFunction<ServerResponse> routes() {
        var routes = route().nest(
            accept(APPLICATION_JSON),
            builder -> builder
                .POST("/", apiHandler::create)
                .PUT("/{id}", apiHandler::update)
        ).build();

        return route()
            .path("/api/rides", () -> routes)
            .build();
    }
}

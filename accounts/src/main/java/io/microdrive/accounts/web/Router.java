package io.microdrive.accounts.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class Router {
    private final AccountsHandler accountsHandler;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        var accounts = route().nest(
            accept(MediaType.APPLICATION_JSON),
            builder -> builder
                .POST("/", accountsHandler::create)
                .GET("/current", accountsHandler::current)
                .POST("/check-password", accountsHandler::checkPassword)
        ).build();

        return route()
            .path("/", () -> accounts)
            .build();
    }
}

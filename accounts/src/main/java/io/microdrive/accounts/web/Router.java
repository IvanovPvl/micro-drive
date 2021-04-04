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
    private final CheckPasswordHandler checkPasswordHandler;

    @Bean
    public RouterFunction<ServerResponse> routes() {
//        var drivers = route().nest(
//            accept(MediaType.APPLICATION_JSON),
//            builder -> builder.POST("/", driversHandler::create)
//                .GET("/{id}", driversHandler::findById)
//                .POST("/{id}/cars", carsHandler::create)
//        ).build();

        var accounts = route().nest(
            accept(MediaType.APPLICATION_JSON),
            builder -> builder.POST("/", accountsHandler::create)
                .POST("/check-password", checkPasswordHandler::checkForClient)
        ).build();

        return route()
            .path("/v1", () -> accounts)
//            .path("/clients", () -> clients)
//            .POST("/drivers", accept(MediaType.APPLICATION_JSON), driversHandler::create)
//            .POST("/auth/token", accept(MediaType.APPLICATION_JSON), authHandler::createToken)
//            .GET("/.well-known/jwks.json", publicKeyHandler::getKey)
            .build();
    }
}

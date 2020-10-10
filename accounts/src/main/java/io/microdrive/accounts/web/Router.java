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
    private final AuthController authController;
    private final DriversHandler driversHandler;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route()
            .POST("/auth/token", accept(MediaType.APPLICATION_JSON), authController::createToken)
            .POST("/drivers", accept(MediaType.APPLICATION_JSON), driversHandler::create)
            .POST("/drivers/{id}", accept(MediaType.APPLICATION_JSON), driversHandler::findById)
            .build();
    }
}

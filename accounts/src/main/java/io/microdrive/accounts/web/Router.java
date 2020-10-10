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
    private final ClientsHandler clientsHandler;
    private final DriversHandler driversHandler;
    private final CarsHandler carsHandler;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        var drivers = route().nest(
            accept(MediaType.APPLICATION_JSON),
            builder -> builder.POST("/", driversHandler::create)
                .GET("/{id}", driversHandler::findById)
                .POST("/{id}/cars", carsHandler::create)
        ).build();

        return route()
            .path("/drivers", () -> drivers)
            .POST("/clients", accept(MediaType.APPLICATION_JSON), clientsHandler::create)
            .POST("/auth/token", accept(MediaType.APPLICATION_JSON), authController::createToken)
            .build();
    }
}

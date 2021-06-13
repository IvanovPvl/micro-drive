package io.microdrive.edge.rides.config;

import io.microdrive.edge.rides.RidesApiHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableConfigurationProperties(RidesConfigurationProperties.class)
public class RidesConfiguration {
    @Bean
    public ReactiveCircuitBreaker ridesCircuitBreaker(ReactiveCircuitBreakerFactory<?, ?> cbf) {
        return cbf.create("rides");
    }

    @Bean
    public RouterFunction<ServerResponse> routes(RidesApiHandler apiHandler) {
        var routes = route().nest(
            accept(MediaType.APPLICATION_JSON),
            builder -> builder
                .POST("", apiHandler::create)
                .PUT("/{id}", apiHandler::update)
                .POST("/get-info", apiHandler::getInfo)
        ).build();

        return route()
            .path("/api/rides", () -> routes)
            .build();
    }
}

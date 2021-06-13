package io.microdrive.edge.driverscontrol.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DriversControlConfigurationProperties.class)
public class DriversControlConfiguration {
    @Bean
    public ReactiveCircuitBreaker driversControlCircuitBreaker(ReactiveCircuitBreakerFactory<?, ?> cbf) {
        return cbf.create("drivers-control");
    }

    @Bean
    public RouteLocator driversControlProxyRouting(
        RouteLocatorBuilder builder,
        DriversControlConfigurationProperties configuration
    ) {
        return builder.routes()
            .route(r -> r.path("/api/drivers-control/add-to-free")
                .and()
                .method("POST")
                .filters(f -> f.circuitBreaker(c -> c.setName("drivers-control")))
                .uri(configuration.getServiceUrl())
            )
            .route(r -> r.path("/api/drivers-control/find-free")
                .and()
                .method("POST")
                .filters(f -> f.circuitBreaker(c -> c.setName("drivers-control")))
                .uri(configuration.getServiceUrl())
            ).build();
    }
}

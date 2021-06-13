package io.microdrive.edge.routing.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RoutingConfigurationProperties.class)
public class RoutingConfiguration {
    @Bean
    public ReactiveCircuitBreaker routingCircuitBreaker(ReactiveCircuitBreakerFactory<?, ?> cbf) {
        return cbf.create("routing");
    }
}

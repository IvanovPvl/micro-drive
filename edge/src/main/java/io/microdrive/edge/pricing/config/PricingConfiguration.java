package io.microdrive.edge.pricing.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PricingConfigurationProperties.class)
public class PricingConfiguration {
    @Bean
    public ReactiveCircuitBreaker pricingCircuitBreaker(ReactiveCircuitBreakerFactory<?, ?> cbf) {
        return cbf.create("pricing");
    }
}

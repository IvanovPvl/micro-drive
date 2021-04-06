package io.microdrive.edge.accounts;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AccountsConfigurationProperties.class)
public class AccountsConfiguration {
    @Bean
    public RouteLocator accountsProxyRouting(
        RouteLocatorBuilder builder,
        AccountsConfigurationProperties configuration
    ) {
        return builder.routes()
            .route(r -> r.path("/api/v1/accounts").and().method("POST").uri(configuration.getServiceUrl()))
            .route(r -> r.path("/api/v1/accounts/check-password").and().method("POST").uri(configuration.getServiceUrl()))
            .build();
    }
}

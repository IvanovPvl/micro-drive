package io.microdrive.edge.driverscontrol.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DriversControlConfigurationProperties.class)
public class DriversControlConfiguration {
    @Bean
    public RouteLocator driversControlProxyRouting(
        RouteLocatorBuilder builder,
        DriversControlConfigurationProperties configuration
    ) {
        return builder.routes()
            .route(r -> r.path("/api/drivers-control/add-to-free")
                .and()
                .method("POST").uri(configuration.getServiceUrl())
            )
            .route(r -> r.path("/api/drivers-control/find-free")
                .and()
                .method("POST").uri(configuration.getServiceUrl())
            ).build();
    }
}

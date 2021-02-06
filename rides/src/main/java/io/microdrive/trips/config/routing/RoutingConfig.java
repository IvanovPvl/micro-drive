package io.microdrive.trips.config.routing;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RoutingConfigProperties.class)
public class RoutingConfig {
}

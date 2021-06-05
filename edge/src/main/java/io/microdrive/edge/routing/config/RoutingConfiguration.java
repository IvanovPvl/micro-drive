package io.microdrive.edge.routing.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RoutingConfigurationProperties.class)
public class RoutingConfiguration {
}

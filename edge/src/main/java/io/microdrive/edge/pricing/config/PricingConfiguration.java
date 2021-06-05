package io.microdrive.edge.pricing.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PricingConfigurationProperties.class)
public class PricingConfiguration {
}

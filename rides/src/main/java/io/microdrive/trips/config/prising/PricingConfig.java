package io.microdrive.trips.config.prising;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PricingConfigProperties.class)
public class PricingConfig {
}

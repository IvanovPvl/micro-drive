package io.microdrive.trip.pricing;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Configuration
@EnableConfigurationProperties(PricingConfigProperties.class)
public class PricingConfig {
}

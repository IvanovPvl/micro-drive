package io.microdrive.pricing.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PricingConfigProperties.class)
public class PricingConfig {
}

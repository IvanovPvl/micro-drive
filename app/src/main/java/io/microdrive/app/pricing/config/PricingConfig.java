package io.microdrive.app.pricing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Configuration
@EnableConfigurationProperties(PricingConfigProperties.class)
public class PricingConfig {
}

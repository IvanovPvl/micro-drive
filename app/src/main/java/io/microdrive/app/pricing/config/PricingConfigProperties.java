package io.microdrive.app.pricing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("pricing")
public class PricingConfigProperties {
    private double priceForKm;
}

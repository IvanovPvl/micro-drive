package io.microdrive.trips.config.prising;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("clients.pricing")
public class PricingConfigProperties {
    private String baseUrl;
}

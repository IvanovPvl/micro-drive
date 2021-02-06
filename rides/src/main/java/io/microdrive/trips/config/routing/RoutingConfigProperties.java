package io.microdrive.trips.config.routing;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("clients.routing")
public class RoutingConfigProperties {
    private String baseUrl;
}

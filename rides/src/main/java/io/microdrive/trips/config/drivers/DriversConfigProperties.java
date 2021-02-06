package io.microdrive.trips.config.drivers;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("clients.drivers")
public class DriversConfigProperties {
    private String baseUrl;
}

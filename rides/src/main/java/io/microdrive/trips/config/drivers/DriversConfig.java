package io.microdrive.trips.config.drivers;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DriversConfigProperties.class)
public class DriversConfig {
}

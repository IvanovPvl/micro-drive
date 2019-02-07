package io.microdrive.routing.tomtom;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("routing.tomtom.client")
public class TomtomConfigurationProperties {
    private String apiKey;
    private String baseUrl;
    private String apiVersion;
    private String contentType;
    private String travelMode;
}

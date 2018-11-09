package io.microdrive.trip.tomtom;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("tomtom.client")
public class TomtomConfigurationProperties {
    private String apiKey;
    private String baseUrl;
    private String apiVersion;
    private String contentType;
    private String travelMode;
}

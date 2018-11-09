package io.microdrive.trip.routing.tomtom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Configuration
@EnableConfigurationProperties(TomtomConfigurationProperties.class)
public class TomtomConfiguration {

    private final TomtomConfigurationProperties config;

    public TomtomConfiguration(TomtomConfigurationProperties config) {
        this.config = config;
    }

    @Bean
    public WebClient tomtomWebClient() {
        return WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .build();
    }
}

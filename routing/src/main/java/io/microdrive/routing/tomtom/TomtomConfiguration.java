package io.microdrive.routing.tomtom;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(TomTomConfigurationProperties.class)
public class TomtomConfiguration {

    private final TomTomConfigurationProperties config;

    @Bean
    public WebClient tomtomWebClient() {
        return WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .build();
    }
}

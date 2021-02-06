package io.microdrive.trips.clients;

import io.microdrive.core.dto.drivers.DriverResponse;
import io.microdrive.core.dto.drivers.ReleaseRequest;
import io.microdrive.trips.config.drivers.DriversConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DriversClient {
    private final DriversConfigProperties properties;
    private final WebClient webClient = WebClient.builder().baseUrl(properties.getBaseUrl()).build();

    public Mono<DriverResponse> request() {
        return webClient
            .post()
            .uri("/request")
            .retrieve()
            .bodyToMono(DriverResponse.class);
    }

    public Mono<Void> release(ReleaseRequest release) {
        return webClient
            .post()
            .uri("/release")
            .bodyValue(release)
            .retrieve()
            .bodyToMono(Void.class);
    }
}

package io.microdrive.edge.rides;

import io.microdrive.core.types.rides.CreateRideRequest;
import io.microdrive.core.types.rides.CreateRideResponse;
import io.microdrive.core.types.rides.UpdateRideRequest;
import io.microdrive.edge.rides.config.RidesConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RidesProxy {
    private final WebClient webClient;
    private final RidesConfigurationProperties properties;

    public Mono<CreateRideResponse> create(CreateRideRequest request) {
        return webClient.post()
            .uri(properties.getServiceUrl())
            .bodyValue(request)
            .exchangeToMono(response -> response.bodyToMono(CreateRideResponse.class));
    }

    public Mono<Void> update(String id, UpdateRideRequest request) {
        return webClient.put()
            .uri(properties.getServiceUrl() + "/" + id)
            .bodyValue(request)
            .exchangeToMono(response -> response.bodyToMono(Void.class));
    }
}

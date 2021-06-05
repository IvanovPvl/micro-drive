package io.microdrive.edge.driverscontrol;

import io.microdrive.core.types.driverscontrol.AddToFreeRequest;
import io.microdrive.core.types.driverscontrol.FindFreeResponse;
import io.microdrive.edge.driverscontrol.config.DriversControlConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DriversControlProxy {
    private final WebClient webClient;
    private final DriversControlConfigurationProperties properties;

    public Mono<FindFreeResponse> findFree() {
        return webClient.post()
            .uri(properties.getServiceUrl() + "/find-free")
            .exchangeToMono(response -> response.bodyToMono(FindFreeResponse.class));
    }

    public Mono<Void> addToFree(String driverId) {
        return webClient.post()
            .uri(properties.getServiceUrl() + "/add-to-free")
            .bodyValue(new AddToFreeRequest(driverId))
            .exchangeToMono(response -> response.bodyToMono(Void.class));
    }
}

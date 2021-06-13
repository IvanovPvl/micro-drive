package io.microdrive.edge.driverscontrol;

import io.microdrive.core.errors.FreeDriverNotFoundException;
import io.microdrive.core.types.driverscontrol.AddToFreeRequest;
import io.microdrive.core.types.driverscontrol.FindFreeResponse;
import io.microdrive.edge.driverscontrol.config.DriversControlConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.error;

@Component
@RequiredArgsConstructor
public class DriversControlProxy {
    private final WebClient webClient;
    private final ReactiveCircuitBreaker driversControlCircuitBreaker;
    private final DriversControlConfigurationProperties properties;

    public Mono<FindFreeResponse> findFree() {
        return webClient.post()
            .uri(properties.getServiceUrl() + "/find-free")
            .retrieve()
            .onStatus(status -> status.value() == 404, r -> error(new FreeDriverNotFoundException()))
            .bodyToMono(FindFreeResponse.class)
            .transform(driversControlCircuitBreaker::run);
    }

    public Mono<Void> addToFree(String driverId) {
        return webClient.post()
            .uri(properties.getServiceUrl() + "/add-to-free")
            .bodyValue(new AddToFreeRequest(driverId))
            .retrieve()
            .bodyToMono(Void.class)
            .transform(driversControlCircuitBreaker::run);
    }
}

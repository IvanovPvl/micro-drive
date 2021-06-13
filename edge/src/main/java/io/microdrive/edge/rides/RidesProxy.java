package io.microdrive.edge.rides;

import io.microdrive.core.errors.RideNotFoundException;
import io.microdrive.core.types.rides.CreateRideRequest;
import io.microdrive.core.types.rides.CreateRideResponse;
import io.microdrive.core.types.rides.UpdateRideRequest;
import io.microdrive.edge.config.security.types.Principal;
import io.microdrive.edge.rides.config.RidesConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.error;

@Component
@RequiredArgsConstructor
public class RidesProxy {
    private final WebClient webClient;
    private final RidesConfigurationProperties properties;
    private final ReactiveCircuitBreaker ridesCircuitBreaker;

    public Mono<CreateRideResponse> create(CreateRideRequest request, Principal principal) {
        return webClient.post()
            .uri(properties.getServiceUrl())
            .bodyValue(request)
            .header("x-account-id", principal.id())
            .header("x-role", principal.role())
            .retrieve()
            .bodyToMono(CreateRideResponse.class)
            .transform(ridesCircuitBreaker::run);
    }

    public Mono<Void> update(String id, UpdateRideRequest request, Principal principal) {
        return webClient.put()
            .uri(properties.getServiceUrl() + "/" + id)
            .bodyValue(request)
            .header("x-account-id", principal.id())
            .header("x-role", principal.role())
            .retrieve()
            .onStatus(HttpStatus::isError, response -> error(new RideNotFoundException(id)))
            .bodyToMono(Void.class)
            .transform(ridesCircuitBreaker::run);
    }
}

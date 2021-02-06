package io.microdrive.trips.clients;

import io.microdrive.core.dto.routing.RouteInfo;
import io.microdrive.core.dto.routing.RouteRequest;
import io.microdrive.trips.config.routing.RoutingConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
@RequiredArgsConstructor
public class RoutingClient {
    private final RoutingConfigProperties properties;
    private final WebClient webClient = WebClient.builder().baseUrl(properties.getBaseUrl()).build();

    public Mono<RouteInfo> getRoute(RouteRequest routeRequest) {
        return webClient
            .post()
            .uri("/get-route")
            .bodyValue(routeRequest)
            .retrieve()
            .bodyToMono(RouteInfo.class);
    }
}

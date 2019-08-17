package io.microdrive.trips.clients;

import io.microdrive.core.dto.routing.RouteInfo;
import io.microdrive.core.dto.routing.RouteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
@RequiredArgsConstructor
public class RoutingClient {

    private final WebClient.Builder webClientBuilder;

    public Mono<RouteInfo> getRoute(RouteRequest routeRequest) {
        return webClientBuilder.baseUrl("http://routing").build().post()
                .uri("/get-route")
                .body(fromObject(routeRequest))
                .retrieve()
                .bodyToMono(RouteInfo.class);

    }

}

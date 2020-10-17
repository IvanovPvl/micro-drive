package io.microdrive.routing.web;

import io.microdrive.core.dto.routing.RouteInfo;
import io.microdrive.core.dto.routing.RouteRequest;
import io.microdrive.routing.RouteProvider;
import io.microdrive.routing.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class ApiHandler {
    private final RouteProvider routeProvider;
    private final RouteService routeService;

    Mono<ServerResponse> getRoute(ServerRequest request) {
        var routeInfoMono = request.bodyToMono(RouteRequest.class)
            .flatMap(r -> routeProvider.calculateRoute(r.getFrom(), r.getTo()))
            .flatMap(r -> Mono.zip(routeService.create(r), Mono.just(r), (route, routeInfo) -> {
                routeInfo.setId(route.getId());
                return routeInfo;
            }));

        return ok().body(routeInfoMono, RouteInfo.class);
    }
}

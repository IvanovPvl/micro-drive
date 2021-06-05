package io.microdrive.routing.web;

import io.microdrive.core.types.routing.RouteResponse;
import io.microdrive.core.types.routing.Point;
import io.microdrive.core.types.routing.RouteInfo;
import io.microdrive.core.types.routing.BuildRouteRequest;
import io.microdrive.routing.RouteProvider;
import io.microdrive.routing.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class ApiHandler {
    private final RouteProvider routeProvider;
    private final RouteService routeService;

    Mono<ServerResponse> buildRoute(ServerRequest request) {
        var routeInfoMono = request.bodyToMono(BuildRouteRequest.class)
            .flatMap(r -> routeProvider.calculateRoute(r.from(), r.to()))
            .flatMap(r -> Mono.zip(routeService.create(r), Mono.just(r), (route, routeInfo) -> new RouteResponse(
                route.getId(),
                routeInfo.points(),
                routeInfo.lengthInMeters(),
                routeInfo.travelTimeInSeconds(),
                routeInfo.trafficDelayInSeconds()
            )));

        return ok().body(routeInfoMono, RouteInfo.class);
    }

    Mono<ServerResponse> getRoute(ServerRequest request) {
        var id = request.pathVariable("id");
        var routeInfoMono = routeService.findById(id)
            .map(route -> {
                var points = route.getPoints()
                    .stream()
                    .map(p -> new Point(p.getLatitude(), p.getLongitude()))
                    .collect(toList());

                return new RouteResponse(
                    route.getId(),
                    points,
                    route.getLengthInMeters(),
                    route.getTravelTimeInSeconds(),
                    route.getTrafficDelayInSeconds()
                );
            });

        return ok().body(routeInfoMono, RouteInfo.class);
    }
}

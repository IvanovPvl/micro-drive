package io.microdrive.routing;

import io.microdrive.routing.dto.Point;
import io.microdrive.routing.dto.RouteInfo;
import reactor.core.publisher.Mono;

public interface RouteProvider {
    Mono<RouteInfo> calculateRoute(String locations);
    Mono<RouteInfo> calculateRoute(Point from, Point to);
}

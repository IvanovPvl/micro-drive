package io.microdrive.trip.routing;

import reactor.core.publisher.Mono;

public interface RouteProvider {
    Mono<RouteInfo> calculateRoute(String locations);
    Mono<RouteInfo> calculateRoute(Point from, Point to);
}

package io.microdrive.routing.service;

import io.microdrive.core.types.routing.RouteInfo;
import io.microdrive.routing.persistence.Route;
import io.microdrive.routing.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository repository;

    public Mono<Route> create(RouteInfo routeInfo) {
        var route = new Route();
        route.setLengthInMeters(routeInfo.lengthInMeters());
        route.setTrafficDelayInSeconds(routeInfo.trafficDelayInSeconds());
        route.setTravelTimeInSeconds(routeInfo.travelTimeInSeconds());
        var points = routeInfo.points()
            .stream()
            .map(point -> new Route.Point(point.latitude(), point.longitude()))
            .collect(toList());
        route.setPoints(points);
        return repository.save(route);
    }

    public Mono<Route> findById(String id) {
        return repository.findById(id);
    }
}

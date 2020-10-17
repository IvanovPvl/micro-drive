package io.microdrive.routing.service;

import io.microdrive.core.dto.routing.RouteInfo;
import io.microdrive.routing.persistence.Route;
import io.microdrive.routing.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository repository;

    public Mono<Route> create(RouteInfo routeInfo) {
        var route = new Route();
        route.setLengthInMeters(routeInfo.getLengthInMeters());
        var points = routeInfo.getPoints()
            .stream()
            .map(point -> new Route.Point(point.getLatitude(), point.getLongitude()))
            .collect(Collectors.toList());
        route.setPoints(points);
        return repository.save(route);
    }
}

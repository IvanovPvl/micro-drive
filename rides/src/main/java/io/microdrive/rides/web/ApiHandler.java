package io.microdrive.rides.web;

import io.microdrive.core.ServerResponseUtils;
import io.microdrive.core.types.rides.CreateRideRequest;
import io.microdrive.core.types.rides.UpdateRideRequest;
import io.microdrive.rides.persistence.Ride;
import io.microdrive.rides.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
class ApiHandler {
    private final RideService rideService;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CreateRideRequest.class)
            .flatMap(rideService::create)
            .flatMap(ServerResponseUtils::fromResult);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        var id = request.pathVariable("id");
        return request.bodyToMono(UpdateRideRequest.class)
            .flatMap(rq -> rideService.updateStatus(id, Ride.Status.valueOf(rq.status())))
            .flatMap(ServerResponseUtils::fromResult);
    }
}

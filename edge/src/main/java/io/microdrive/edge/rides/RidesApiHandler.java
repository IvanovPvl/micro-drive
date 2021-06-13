package io.microdrive.edge.rides;

import io.microdrive.core.ServerResponseUtils;
import io.microdrive.core.types.rides.UpdateRideRequest;
import io.microdrive.core.types.routing.BuildRouteRequest;
import io.microdrive.edge.types.NewRideRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RidesApiHandler {
    private final RidesService ridesService;

    public Mono<ServerResponse> getInfo(ServerRequest request) {
        return request.bodyToMono(BuildRouteRequest.class)
            .flatMap(ridesService::getInfo)
            .flatMap(ServerResponseUtils::fromResult);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(NewRideRequest.class)
            .flatMap(ridesService::create)
            .flatMap(ServerResponseUtils::fromResult);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        var id = request.pathVariable("id");
        return request.bodyToMono(UpdateRideRequest.class)
            .flatMap(rq -> ridesService.update(id, rq))
            .flatMap(ServerResponseUtils::fromResult);
    }
}

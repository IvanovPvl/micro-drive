package io.microdrive.edge.rides;

import io.microdrive.core.types.routing.BuildRouteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class RidesApiHandler {
    private final RidesService ridesService;

    public Mono<ServerResponse> getInfo(ServerRequest request) {
        return request.bodyToMono(BuildRouteRequest.class)
            .flatMap(ridesService::getInfo)
            .flatMap(response -> ok().bodyValue(response));
    }
}

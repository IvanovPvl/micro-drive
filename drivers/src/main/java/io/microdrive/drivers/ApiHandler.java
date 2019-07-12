package io.microdrive.drivers;

import io.microdrive.drivers.dto.RequestFree;
import io.microdrive.drivers.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ApiHandler {

    private final DriverService driverService;

    Mono<ServerResponse> free(ServerRequest request) {
        return request.bodyToMono(RequestFree.class)
                .map(RequestFree::getDriverId)
                .flatMap(driverService::addDriverToFree)
                .flatMap(l -> Mono.empty());
    }

}

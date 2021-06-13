package io.microdrive.driverscontrol.web;

import io.microdrive.core.errors.ErrorResponse;
import io.microdrive.core.errors.FreeDriverNotFoundException;
import io.microdrive.core.types.driverscontrol.AddToFreeRequest;
import io.microdrive.driverscontrol.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
class ApiHandler {
    private final DriverService driverService;

    Mono<ServerResponse> findFree(ServerRequest request) {
        return driverService.findFree()
            .flatMap(response -> ok().bodyValue(response))
            .onErrorResume(error -> {
                if (error instanceof FreeDriverNotFoundException) {
                    return status(HttpStatus.NOT_FOUND).bodyValue(new ErrorResponse(error.getMessage()));
                }

                return status(HttpStatus.BAD_REQUEST).bodyValue(new ErrorResponse(error.getMessage()));
            });
    }

    Mono<ServerResponse> addToFree(ServerRequest request) {
        // TODO: get id from security context
        var result = request.bodyToMono(AddToFreeRequest.class)
            .map(AddToFreeRequest::driverId)
            .flatMap(driverService::addToFree);

        return ok().build(result.then());
    }
}

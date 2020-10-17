package io.microdrive.drivers.web;

import io.microdrive.core.dto.drivers.ReleaseRequest;
import io.microdrive.core.dto.errors.ResponseError;
import io.microdrive.drivers.errors.FreeDriverNotFoundError;
import io.microdrive.drivers.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.val;
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

    Mono<ServerResponse> request(ServerRequest request) {
        return driverService.request()
            .flatMap(driver -> ok().bodyValue(driver))
            .onErrorResume(error -> {
                if (error instanceof FreeDriverNotFoundError) {
                    return status(HttpStatus.NOT_FOUND).bodyValue(new ResponseError("Free drivers not found."));
                }

                return status(HttpStatus.BAD_REQUEST).bodyValue(new ResponseError(error.getMessage()));
            });
    }

    Mono<ServerResponse> release(ServerRequest request) {
        val result = request.bodyToMono(ReleaseRequest.class)
            .map(ReleaseRequest::getDriverId)
            .flatMap(driverService::release);

        return ok().build(result.then());
    }
}

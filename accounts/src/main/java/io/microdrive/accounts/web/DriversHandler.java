package io.microdrive.accounts.web;

import io.microdrive.accounts.service.DriverService;
import io.microdrive.accounts.web.types.driver.CreateDriverRequest;
import io.microdrive.accounts.web.types.driver.CreateDriverResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
public class DriversHandler {
    private final DriverService driverService;

    public Mono<ServerResponse> create(ServerRequest request) {
        var mono = request.bodyToMono(CreateDriverRequest.class)
            .flatMap(driverService::create);

        return status(HttpStatus.CREATED).body(mono, CreateDriverResponse.class);
    }
}

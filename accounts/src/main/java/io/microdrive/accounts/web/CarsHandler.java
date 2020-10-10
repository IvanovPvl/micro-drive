package io.microdrive.accounts.web;

import io.microdrive.accounts.service.CarService;
import io.microdrive.accounts.web.dto.CreateCarRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
public class CarsHandler {
    private final CarService carService;

    Mono<ServerResponse> create(ServerRequest serverRequest) {
        var accountId = serverRequest.pathVariable("id");
        var mono = serverRequest.bodyToMono(CreateCarRequest.class)
            .flatMap(request -> carService.create(request, accountId))
            .then();

        return status(HttpStatus.CREATED).body(mono, Void.class);
    }
}

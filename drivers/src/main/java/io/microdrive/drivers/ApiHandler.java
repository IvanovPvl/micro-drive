package io.microdrive.drivers;

import io.microdrive.core.dto.drivers.Account;
import io.microdrive.core.dto.drivers.ReleaseRequest;
import io.microdrive.drivers.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class ApiHandler {

    private final DriverService driverService;

    Mono<ServerResponse> request(ServerRequest request) {
        val account = driverService.request();
        return ok().body(account, Account.class);
    }

    Mono<ServerResponse> release(ServerRequest request) {
        return request.bodyToMono(ReleaseRequest.class)
                .map(ReleaseRequest::getDriverId)
                .flatMap(driverService::release)
                .flatMap(l -> Mono.empty());
    }

}

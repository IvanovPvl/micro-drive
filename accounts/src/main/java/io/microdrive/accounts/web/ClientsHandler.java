package io.microdrive.accounts.web;

import io.microdrive.accounts.ServerResponseUtils;
import io.microdrive.accounts.service.ClientService;
import io.microdrive.accounts.web.types.client.CreateClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientsHandler {
    private final ClientService clientService;

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateClientRequest.class)
            .flatMap(clientService::create)
            .flatMap(ServerResponseUtils::fromResult);
    }
}

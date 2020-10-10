package io.microdrive.accounts.web;

import io.microdrive.accounts.service.AccountService;
import io.microdrive.accounts.web.dto.CreateAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
public class ClientsHandler {
    private final AccountService accountService;

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        var mono = serverRequest.bodyToMono(CreateAccountRequest.class)
            .flatMap(accountService::createClient)
            .then();

        return status(HttpStatus.CREATED).body(mono, Void.class);
    }
}

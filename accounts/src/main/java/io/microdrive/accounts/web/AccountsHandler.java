package io.microdrive.accounts.web;

import io.microdrive.accounts.service.AccountService;
import io.microdrive.accounts.web.types.CreateAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Service
@RequiredArgsConstructor
public class AccountsHandler {
    private final AccountService accountService;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CreateAccountRequest.class)
            .flatMap(accountService::create)
            .flatMap(response -> status(HttpStatus.CREATED).bodyValue(response));
    }
}

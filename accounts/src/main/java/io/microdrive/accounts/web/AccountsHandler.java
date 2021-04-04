package io.microdrive.accounts.web;

import io.microdrive.accounts.ServerResponseUtils;
import io.microdrive.accounts.service.AccountService;
import io.microdrive.accounts.service.CheckPasswordService;
import io.microdrive.accounts.web.types.CheckPasswordRequest;
import io.microdrive.accounts.web.types.CreateAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountsHandler {
    private final AccountService accountService;
    private final CheckPasswordService checkPasswordService;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CreateAccountRequest.class)
            .flatMap(accountService::create)
            .flatMap(ServerResponseUtils::fromResult);
    }

    public Mono<ServerResponse> checkPassword(ServerRequest request) {
        return request.bodyToMono(CheckPasswordRequest.class)
            .flatMap(checkPasswordService::check)
            .flatMap(ServerResponseUtils::fromResult);
    }
}

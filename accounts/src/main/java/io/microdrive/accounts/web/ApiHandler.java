package io.microdrive.accounts.web;

import io.microdrive.accounts.domain.Account;
import io.microdrive.accounts.service.AccountService;
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

    private final AccountService accountService;

    public Mono<ServerResponse> getDriverById(ServerRequest request) {
        val driver = accountService.findDriver(request.pathVariable("id"));
        return ok().body(driver, Account.class);
    }

}

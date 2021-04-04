package io.microdrive.accounts.web;

import io.microdrive.accounts.errors.ClientNotFoundException;
import io.microdrive.accounts.service.CheckPasswordService;
import io.microdrive.accounts.web.types.CheckPasswordRequest;
import io.microdrive.core.dto.errors.ResponseError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Service
@RequiredArgsConstructor
public class CheckPasswordHandler {
    private final CheckPasswordService checkPasswordService;

    public Mono<ServerResponse> checkForClient(ServerRequest request) {
        return request.bodyToMono(CheckPasswordRequest.class)
            .flatMap(checkPasswordService::checkForClient)
            .flatMap(result -> status(HttpStatus.OK).build())
            .onErrorResume(error -> {
                if (error instanceof ClientNotFoundException ex) {
                    return status(HttpStatus.NOT_FOUND).bodyValue(new ResponseError(ex.getMessage()));
                }
                return status(HttpStatus.BAD_REQUEST).bodyValue(new ResponseError(error.getMessage()));
            });
    }
}

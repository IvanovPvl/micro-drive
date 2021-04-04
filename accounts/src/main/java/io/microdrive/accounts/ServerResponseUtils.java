package io.microdrive.accounts;

import io.microdrive.accounts.errors.AccountAlreadyExistsException;
import io.microdrive.accounts.errors.AccountNotFoundException;
import io.microdrive.core.dto.errors.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.status;

public class ServerResponseUtils {
    public static <T> Mono<ServerResponse> fromResult(Result<T> result) {
        if (result.isFailed()) {
            if (result.left() instanceof AccountNotFoundException ex) {
                return status(HttpStatus.NOT_FOUND).bodyValue(new ErrorResponse(ex.getMessage()));
            } else if (result.left() instanceof AccountAlreadyExistsException ex) {
                return status(HttpStatus.BAD_REQUEST).bodyValue(new ErrorResponse(ex.getMessage()));
            }

            return status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(new ErrorResponse(result.left().getMessage()));
        }

        return status(HttpStatus.OK).bodyValue(result.right());
    }
}

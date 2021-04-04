package io.microdrive.accounts;

import io.microdrive.accounts.errors.ClientAlreadyExistsException;
import io.microdrive.accounts.errors.ClientNotFoundException;
import io.microdrive.core.dto.errors.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.status;

public class ServerResponseUtils {
    public static <E extends Throwable, T> Mono<ServerResponse> fromResult(Result<E, T> result) {
        if (result.isFailed()) {
            if (result.left() instanceof ClientNotFoundException ex) {
                return status(HttpStatus.NOT_FOUND).bodyValue(new ResponseError(ex.getMessage()));
            } else if (result.left() instanceof ClientAlreadyExistsException ex) {
                return status(HttpStatus.BAD_REQUEST).bodyValue(new ResponseError(ex.getMessage()));
            }

            return status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(new ResponseError(result.left().getMessage()));
        }

        return status(HttpStatus.OK).bodyValue(result.right());
    }
}

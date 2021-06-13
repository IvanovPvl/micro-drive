package io.microdrive.core;

import io.microdrive.core.errors.AccountAlreadyExistsException;
import io.microdrive.core.errors.ErrorResponse;
import io.microdrive.core.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.status;

public class ServerResponseUtils {
    public static <T> Mono<ServerResponse> fromResult(Result<T> result) {
        if (result.isFailed()) {
            var error = result.left();
            var status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (error instanceof ResourceNotFoundException) {
                status = HttpStatus.NOT_FOUND;
            } else if (error instanceof AccountAlreadyExistsException) {
                status = HttpStatus.BAD_REQUEST;
            }

            return status(status).bodyValue(new ErrorResponse(error.getMessage()));
        }

        return status(HttpStatus.OK).bodyValue(result.right());
    }

    public static <T> Mono<ServerResponse> ok(T data) {
        return status(HttpStatus.OK).bodyValue(data);
    }
}

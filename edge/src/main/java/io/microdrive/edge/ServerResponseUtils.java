package io.microdrive.edge;

import io.microdrive.core.Result;
import io.microdrive.core.dto.errors.ErrorResponse;
import io.microdrive.core.errors.FreeDriverNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.status;

public class ServerResponseUtils {
    public static <T> Mono<ServerResponse> fromResult(Result<T> result) {
        if (result.isFailed()) {
            if (result.left() instanceof FreeDriverNotFoundException e) {
                return status(HttpStatus.NOT_FOUND).bodyValue(new ErrorResponse(e.getMessage()));
            }

            return status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(new ErrorResponse(result.left().getMessage()));
        }

        return status(HttpStatus.OK).bodyValue(result.right());
    }

    public static <T> Mono<ServerResponse> ok(T data) {
        return status(HttpStatus.OK).bodyValue(data);
    }
}

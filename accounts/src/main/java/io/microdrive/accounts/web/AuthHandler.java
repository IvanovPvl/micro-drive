package io.microdrive.accounts.web;

//import io.microdrive.core.dto.errors.ResponseError;
import io.microdrive.accounts.service.TokenService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

    import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
public class AuthHandler {
    private final TokenService tokenService;

    Mono<ServerResponse> createToken(ServerRequest request) {
        return Mono.empty();
//        return request.bodyToMono(AuthRequest.class)
//            .flatMap(tokenService::create)
//            .flatMap(token -> ok().bodyValue(token))
//            .onErrorResume(error -> {
//                if (error instanceof AccountNotFound e) {
//                    return status(HttpStatus.NOT_FOUND).bodyValue(e.toResponseError());
//                }
//
//                return status(HttpStatus.BAD_REQUEST).bodyValue(new ResponseError(error.getMessage()));
//            });
    }
}

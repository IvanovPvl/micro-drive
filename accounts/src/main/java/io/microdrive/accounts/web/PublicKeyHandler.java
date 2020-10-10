package io.microdrive.accounts.web;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class PublicKeyHandler {
    private final KeyPair keyPair;

    public Mono<ServerResponse> getKey(ServerRequest request) {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return Mono.just(new JWKSet(key).toJSONObject())
            .flatMap(response -> ok().bodyValue(response));
    }
}

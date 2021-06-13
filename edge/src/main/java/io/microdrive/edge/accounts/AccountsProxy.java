package io.microdrive.edge.accounts;

import io.microdrive.core.types.accounts.CheckPasswordRequest;
import io.microdrive.core.types.accounts.CheckPasswordResponse;
import io.microdrive.edge.accounts.config.AccountsConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountsProxy {
    private final WebClient webClient;
    private final AccountsConfigurationProperties properties;
    private final ReactiveCircuitBreaker accountsCircuitBreaker;

    public Mono<CheckPasswordResponse> checkPassword(CheckPasswordRequest request) {
        return webClient.post().uri(properties.getServiceUrl() + "/check-password")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(CheckPasswordResponse.class)
            .transform(accountsCircuitBreaker::run);
    }
}

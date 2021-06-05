package io.microdrive.edge.accounts;

import io.microdrive.core.types.accounts.CheckPasswordRequest;
import io.microdrive.core.types.accounts.CheckPasswordResponse;
import io.microdrive.edge.accounts.config.AccountsConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AccountsProxy {
    private final WebClient webClient;
    private final AccountsConfigurationProperties properties;

    public AccountsProxy(WebClient webClient, AccountsConfigurationProperties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    public Mono<CheckPasswordResponse> checkPassword(CheckPasswordRequest request) {
        return webClient.post().uri(properties.getServiceUrl() + "/check-password")
            .bodyValue(request)
            .exchangeToMono(clientResponse -> clientResponse.bodyToMono(CheckPasswordResponse.class));
    }
}

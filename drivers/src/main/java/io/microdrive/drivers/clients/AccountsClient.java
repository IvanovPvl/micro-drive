package io.microdrive.drivers.clients;

import io.microdrive.core.dto.drivers.DriverResponse;
import io.microdrive.drivers.config.accounts.AccountsConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountsClient {
    private final AccountsConfigProperties properties;
    private final WebClient webClient = WebClient.builder().baseUrl(properties.getBaseUrl()).build();

    public Mono<DriverResponse> getDriver(String id) {
        return webClient.get()
            .uri("/drivers/" + id)
            .retrieve()
            .bodyToMono(DriverResponse.class);
    }
}

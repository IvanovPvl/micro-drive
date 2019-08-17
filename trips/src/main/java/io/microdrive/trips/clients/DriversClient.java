package io.microdrive.trips.clients;

import io.microdrive.core.dto.drivers.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DriversClient {

    private final WebClient.Builder webClientBuilder;

    public Mono<Account> request() {
        return webClientBuilder.baseUrl("http://drivers").build()
                .post()
                .uri("/request")
                .retrieve()
                .bodyToMono(Account.class);
    }

}

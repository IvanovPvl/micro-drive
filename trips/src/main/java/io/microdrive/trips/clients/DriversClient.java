package io.microdrive.trips.clients;

import io.microdrive.core.dto.drivers.Account;
import io.microdrive.core.dto.drivers.ReleaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

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

    public Mono<Void> release(ReleaseRequest release) {
        return webClientBuilder.baseUrl("http://drivers").build()
                .post()
                .uri("/release")
                .body(fromObject(release))
                .retrieve()
                .bodyToMono(Void.class);
    }

}

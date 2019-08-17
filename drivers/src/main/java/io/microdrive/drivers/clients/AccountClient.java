package io.microdrive.drivers.clients;

import io.microdrive.core.dto.drivers.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountClient {

    private final WebClient.Builder webClientBuilder;

    public Mono<Account> getAccount(String id) {
        return webClientBuilder.baseUrl("http://accounts").build().get()
                .uri("/drivers/" + id)
                .retrieve()
                .bodyToMono(Account.class);

    }

}

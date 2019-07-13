package io.microdrive.drivers.clients;

import io.microdrive.drivers.dto.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountClient {

    private final WebClient webClient;

    public Mono<Account> getAccount(String id) {
        return webClient.get()
                .uri("/drivers/%s" + id)
                .retrieve()
                .bodyToMono(Account.class);
    }

}

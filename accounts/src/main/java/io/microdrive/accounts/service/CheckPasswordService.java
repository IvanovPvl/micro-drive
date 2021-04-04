package io.microdrive.accounts.service;

import io.microdrive.accounts.Result;
import io.microdrive.accounts.errors.ClientNotFoundException;
import io.microdrive.accounts.web.types.CheckPasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CheckPasswordService {
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;

    public Mono<Boolean> checkForClient(CheckPasswordRequest request) {
        return clientService.findByPhoneNumber(request.getPhoneNumber())
            .flatMap(client -> {
                if (!passwordEncoder.matches(request.getPassword(), client.getPassword())) {
                    return Mono.error(new ClientNotFoundException());
                }
                return Mono.just(true);
            })
            .switchIfEmpty(Mono.error(new ClientNotFoundException()));
    }
}

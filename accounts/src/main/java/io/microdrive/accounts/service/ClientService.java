package io.microdrive.accounts.service;

import io.microdrive.accounts.Result;
import io.microdrive.accounts.errors.ClientAlreadyExistsException;
import io.microdrive.accounts.persistence.Client;
import io.microdrive.accounts.repository.ClientRepository;
import io.microdrive.accounts.web.types.client.CreateClientRequest;
import io.microdrive.accounts.web.types.client.CreateClientResponse;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<Result<CreateClientResponse>> create(CreateClientRequest request) {
        var client = new Client(request);
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        return clientRepository.save(client)
            .map(c -> Result.success(new CreateClientResponse(c)))
            .onErrorResume(throwable -> {
                if (throwable instanceof DuplicateKeyException) {
                    return Mono.just(Result.fail(new ClientAlreadyExistsException(request.getPhoneNumber())));
                }
                return Mono.just(Result.fail(throwable));
            });
    }

    public Mono<Client> findByPhoneNumber(String phoneNumber) {
        return clientRepository.findByPhoneNumber(phoneNumber);
    }
}

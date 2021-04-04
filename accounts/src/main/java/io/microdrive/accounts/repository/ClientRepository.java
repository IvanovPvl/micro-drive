package io.microdrive.accounts.repository;

import io.microdrive.accounts.persistence.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
    Mono<Client> findByPhoneNumber(String phoneNumber);
}

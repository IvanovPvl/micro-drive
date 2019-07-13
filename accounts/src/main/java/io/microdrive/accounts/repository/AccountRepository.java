package io.microdrive.accounts.repository;

import io.microdrive.accounts.domain.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
    Mono<Account> findByIdAndRole(String id, String role);
}

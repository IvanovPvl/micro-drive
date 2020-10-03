package io.microdrive.accounts.repository;

import io.microdrive.accounts.persistence.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
    Mono<Account> findByPhoneNumber(String phoneNumber);
}

package io.microdrive.accounts.repository;

import io.microdrive.accounts.persistence.Car;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CarRepository extends ReactiveMongoRepository<Car, String> {
    Mono<Car> findByAccountId(String accountId);
}

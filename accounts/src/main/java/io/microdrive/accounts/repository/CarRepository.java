package io.microdrive.accounts.repository;

import io.microdrive.accounts.persistence.Car;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CarRepository extends ReactiveMongoRepository<Car, String> {}

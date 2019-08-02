package io.microdrive.accounts.repository;

import io.microdrive.accounts.domain.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<Car, String> {}

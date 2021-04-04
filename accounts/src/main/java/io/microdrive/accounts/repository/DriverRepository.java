package io.microdrive.accounts.repository;

import io.microdrive.accounts.persistence.Driver;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DriverRepository extends ReactiveMongoRepository<Driver, String> {}

package io.microdrive.rides.repository;

import io.microdrive.rides.persistence.Ride;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RideRepository extends ReactiveMongoRepository<Ride, String> {}

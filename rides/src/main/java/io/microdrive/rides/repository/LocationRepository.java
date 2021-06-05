package io.microdrive.rides.repository;

import io.microdrive.rides.persistence.Location;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LocationRepository extends ReactiveMongoRepository<Location, String> {}

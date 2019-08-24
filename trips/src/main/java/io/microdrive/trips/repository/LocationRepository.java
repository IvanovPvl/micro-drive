package io.microdrive.trips.repository;

import io.microdrive.trips.domain.Location;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LocationRepository extends ReactiveMongoRepository<Location, String> {}

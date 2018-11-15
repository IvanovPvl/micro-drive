package io.microdrive.trip.api;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PointRepository extends ReactiveMongoRepository<Point, String> {
    Flux<Point> findAllByTripId(String tripId);
}

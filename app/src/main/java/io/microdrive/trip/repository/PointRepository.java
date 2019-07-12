package io.microdrive.trip.repository;

import reactor.core.publisher.Flux;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.microdrive.trip.domain.Point;

@Repository
public interface PointRepository extends ReactiveMongoRepository<Point, String> {
    Flux<Point> findAllByTripId(String tripId);
}

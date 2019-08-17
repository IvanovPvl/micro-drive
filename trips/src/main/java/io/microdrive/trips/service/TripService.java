package io.microdrive.trips.service;

import io.microdrive.trips.domain.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TripService {

    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<Trip> addTrip(Trip trip) {
        return mongoTemplate.save(trip);
    }

}

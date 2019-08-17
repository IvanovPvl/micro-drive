package io.microdrive.trips.service;

import io.microdrive.trips.domain.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TripService {

    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<Trip> addTrip(Trip trip) {
        return mongoTemplate.save(trip);
    }

    public Mono<Boolean> claim(String id, String driverId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id))
                .addCriteria(Criteria.where("status").is(Trip.Status.NEW.name()));
        Update update = new Update();
        update.set("driverId", driverId);
        update.set("status", Trip.Status.CLAIMED);

        return update(query, update);
    }

    /**
     * Set IN_PROGRESS status to trip
     *
     * @param id tripId
     * @return Mono result
     */
    public Mono<Boolean> start(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id))
                .addCriteria(Criteria.where("status").is(Trip.Status.CLAIMED.name()));
        Update update = new Update();
        update.set("status", Trip.Status.IN_PROGRESS);

        return update(query, update);
    }

    /**
     * Set FINISHED status to trip
     *
     * @param id tripId
     * @return Mono result
     */
    public Mono<Boolean> finish(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id))
                .addCriteria(Criteria.where("status").is(Trip.Status.IN_PROGRESS.name()));
        Update update = new Update();
        update.set("status", Trip.Status.FINISHED);

        return update(query, update);
    }

    private Mono<Boolean> update(Query query, Update update) {
        return this.mongoTemplate.updateFirst(query, update, Trip.class)
                .flatMap(r -> r.getModifiedCount() == 1 ? Mono.just(true) : Mono.just(false));
    }

}

package io.microdrive.trips.service;

import io.microdrive.trips.persistence.Ride;
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

    public Mono<Ride> addTrip(Ride trip) {
        return mongoTemplate.save(trip);
    }

    public Mono<Boolean> claim(String id, String driverId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id))
                .addCriteria(Criteria.where("status").is(Ride.Status.NEW.name()));
        Update update = new Update();
        update.set("driverId", driverId);
        update.set("status", Ride.Status.CLAIMED);

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
                .addCriteria(Criteria.where("status").is(Ride.Status.CLAIMED.name()));
        Update update = new Update();
        update.set("status", Ride.Status.IN_PROGRESS);

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
                .addCriteria(Criteria.where("status").is(Ride.Status.IN_PROGRESS.name()));
        Update update = new Update();
        update.set("status", Ride.Status.FINISHED);

        return update(query, update);
    }

    /**
     * Find claimed trip for driver
     *
     * @param driverId driverId
     * @return Mono
     */
    public Mono<Ride> findClaimedTripForDriver(String driverId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(driverId))
                .addCriteria(Criteria.where("status").is(Ride.Status.CLAIMED.name()));

        return mongoTemplate.findOne(query, Ride.class);
    }

    private Mono<Boolean> update(Query query, Update update) {
        return mongoTemplate.updateFirst(query, update, Ride.class)
                .flatMap(r -> r.getModifiedCount() == 1 ? Mono.just(true) : Mono.just(false));
    }

}

package io.microdrive.trip.service;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

import io.microdrive.trip.domain.TripInfo;

@Service
public class TripService {

    private final ReactiveMongoTemplate mongoTemplate;

    public TripService(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Store trip
     *
     * @param tripInfo info about trip
     * @return Mono result
     */
    public Mono<TripInfo> addTrip(TripInfo tripInfo) {
        return this.mongoTemplate.save(tripInfo);
    }

    /**
     * Assign new trip to driver and set status to NEW
     *
     * @param id tripId
     * @param driverId driverId
     * @return Mono result
     */
    public Mono<Boolean> expectTrip(String id, String driverId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id))
                .addCriteria(Criteria.where("status").is(TripInfo.Status.NEW.name()));
        Update update = new Update();
        update.set("driverId", driverId);
        update.set("status", TripInfo.Status.EXPECTED);

        return this.update(query, update);
    }

    /**
     * Set IN_PROGRESS status to trip
     *
     * @param id tripId
     * @return Mono result
     */
    public Mono<Boolean> startTrip(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id))
                .addCriteria(Criteria.where("status").is(TripInfo.Status.EXPECTED.name()));
        Update update = new Update();
        update.set("status", TripInfo.Status.IN_PROGRESS);

        return this.update(query, update);
    }

    /**
     * Set FINISHED status to trip
     *
     * @param id tripId
     * @return Mono result
     */
    public Mono<Boolean> finishTrip(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id))
                .addCriteria(Criteria.where("status").is(TripInfo.Status.IN_PROGRESS.name()));
        Update update = new Update();
        update.set("status", TripInfo.Status.FINISHED);

        return this.update(query, update);
    }

    private Mono<Boolean> update(Query query, Update update) {
        return this.mongoTemplate.updateFirst(query, update, TripInfo.class)
                .flatMap(r -> r.getModifiedCount() == 1 ? Mono.just(true) : Mono.just(false));
    }
}

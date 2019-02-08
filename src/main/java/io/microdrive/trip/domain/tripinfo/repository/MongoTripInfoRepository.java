package io.microdrive.trip.domain.tripinfo.repository;

import io.microdrive.trip.domain.tripinfo.TripInfo;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Repository
public class MongoTripInfoRepository implements TripInfoRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    public MongoTripInfoRepository(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Store trip
     *
     * @param tripInfo info about trip
     * @return Mono result
     */
    @Override
    public Mono<TripInfo> save(TripInfo tripInfo) {
        return this.mongoTemplate.save(tripInfo);
    }

    @Override
    public Mono<TripInfo> findOneByDriverIdAndStatus(String driverId, TripInfo.Status status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("driverId").is(driverId))
                .addCriteria(Criteria.where("status").is(status.name()));
        return this.mongoTemplate.findOne(query, TripInfo.class);
    }

    @Override
    public Mono<Boolean> updateFromToStatus(String tripId, TripInfo.Status from, TripInfo.Status to) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(tripId))
                .addCriteria(Criteria.where("status").is(from.name()));

        Update update = new Update();
        update.set("status", to);

        return this.mongoTemplate.updateFirst(query, update, TripInfo.class)
                .flatMap(r -> r.getModifiedCount() == 1 ? Mono.just(true) : Mono.just(false));
    }

    @Override
    public Mono<Boolean> updateFromToStatusForDriver(String tripId,
                                                     String driverId,
                                                     TripInfo.Status from,
                                                     TripInfo.Status to) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(tripId))
                .addCriteria(Criteria.where("status").is(from.name()));
        Update update = new Update();
        update.set("driverId", driverId);
        update.set("status", to);

        return this.mongoTemplate.updateFirst(query, update, TripInfo.class)
                .flatMap(r -> r.getModifiedCount() == 1 ? Mono.just(true) : Mono.just(false));
    }
}

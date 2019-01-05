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

    public Mono<TripInfo> addTrip(TripInfo tripInfo) {
        return this.mongoTemplate.save(tripInfo);
    }

    public Mono<Boolean> expectTrip(String id, String driverId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("driverId", driverId);
        update.set("status", TripInfo.Status.EXPECTED);

        return this.mongoTemplate.updateFirst(query, update, TripInfo.class)
                .flatMap(r -> r.getModifiedCount() == 1 ? Mono.just(true) : Mono.just(false));
    }

    public void startTrip(String tripId) {}
}

package io.microdrive.trip.repository;

import reactor.core.publisher.Mono;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import io.microdrive.trip.domain.TripInfo;

@Repository
public class TripInfoReactiveMongoRepository implements TripInfoRepository{

    private final ReactiveMongoTemplate mongoTemplate;

    public TripInfoReactiveMongoRepository(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mono<TripInfo> findByIb(String id) {
        return this.mongoTemplate.findById(id, TripInfo.class);
    }

    @Override
    public Mono<TripInfo> save(TripInfo tripInfo) {
        return this.mongoTemplate.save(tripInfo);
    }

    @Override
    public Mono<UpdateResult> updateDriverIdAndStatus(String id, String driverId, TripInfo.Status status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("driverId", driverId);
        update.set("status", status);
        return this.mongoTemplate.updateFirst(query, update, TripInfo.class);
    }
}

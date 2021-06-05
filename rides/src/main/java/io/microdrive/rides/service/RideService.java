package io.microdrive.rides.service;

import io.microdrive.core.types.rides.CreateRideRequest;
import io.microdrive.coresecurity.types.Principal;
import io.microdrive.rides.persistence.Ride;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.security.core.context.ReactiveSecurityContextHolder.getContext;

@Service
@RequiredArgsConstructor
public class RideService {
    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<Ride> create(CreateRideRequest request) {
        return getContext()
            .flatMap(securityContext -> {
                var principal = (Principal) securityContext.getAuthentication().getPrincipal();
                var ride = new Ride(request.routeId(), principal.getId(), request.driverId(), request.price());
                return mongoTemplate.save(ride);
            });
    }

    public Mono<Void> updateStatus(String id, Ride.Status status) {
        var query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        var update = new Update();
        update.set("status", status);
        return mongoTemplate.updateFirst(query, update, Ride.class).then();
    }
}

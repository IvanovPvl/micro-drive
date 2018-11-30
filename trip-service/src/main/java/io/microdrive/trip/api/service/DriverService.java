package io.microdrive.trip.api.service;

import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.ReactiveSetOperations;

import java.util.Optional;

@Service
public class DriverService {

    private static final String BUSY_COLLECTION = "busy";
    private static final String FREE_COLLECTION = "free";

    private final ReactiveSetOperations<String, String> setOperations;

    public DriverService(ReactiveSetOperations<String, String> setOperations) {
        this.setOperations = setOperations;
    }

    public Mono<Long> addToBusy(String driverId) {
        // TODO: use transaction
        return setOperations.remove(FREE_COLLECTION, driverId)
                .flatMap(l -> setOperations.add(BUSY_COLLECTION, driverId));
    }

    public Mono<Long> addToFree(String driverId) {
        // TODO: use transaction
        return setOperations.remove(BUSY_COLLECTION, driverId)
                .flatMap(l -> setOperations.add(FREE_COLLECTION, driverId));
    }

//    public Mono<Optional<User>> getFreeDriver() {
//    }
}

package io.microdrive.driverscontrol.service;

import io.microdrive.core.types.driverscontrol.FindFreeResponse;
import io.microdrive.core.errors.FreeDriverNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveSetOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DriverService {
    private static final String FREE_DRIVERS = "free_drivers";

    private final ReactiveSetOperations<String, String> setOperations;

    /**
     * Add driver to free set.
     *
     * @param driverId driverId
     * @return Mono
     */
    public Mono<Long> addToFree(String driverId) {
        return setOperations.add(FREE_DRIVERS, driverId);
    }

    /**
     * Get free driver.
     *
     * @return Mono
     */
    public Mono<FindFreeResponse> findFree() {
        return setOperations.pop(FREE_DRIVERS)
            .map(FindFreeResponse::new)
            .switchIfEmpty(Mono.error(new FreeDriverNotFoundException()));
    }
}

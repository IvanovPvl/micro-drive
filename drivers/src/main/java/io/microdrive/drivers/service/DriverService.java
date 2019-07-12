package io.microdrive.drivers.service;

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
     * Add driver to free set
     *
     * @param driverId driverId
     * @return Mono
     */
    public Mono<Long> addDriverToFree(String driverId) {
        return setOperations.add(FREE_DRIVERS, driverId);
    }

}

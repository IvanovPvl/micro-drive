package io.microdrive.trip.service;

import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.ReactiveSetOperations;

import java.util.Optional;

import io.microdrive.auth.domain.User;
import io.microdrive.auth.repository.UserRepository;

@Service
public class DriverService {

    private static final String FREE_DRIVERS = "free_drivers";

    private final UserRepository userRepository;
    private final ReactiveSetOperations<String, String> setOperations;

    public DriverService(ReactiveSetOperations<String, String> setOperations, UserRepository userRepository) {
        this.setOperations = setOperations;
        this.userRepository = userRepository;
    }

    /**
     * Get driver from free set
     *
     * @return Mono
     */
    public Mono<Optional<User>> getFreeDriver() {
        return this.setOperations.pop(FREE_DRIVERS)
                .map(this.userRepository::findById)
                .switchIfEmpty(Mono.just(Optional.empty()));
    }

    /**
     * Add driver to free set
     *
     * @param driverId driverId
     * @return Mono
     */
    public Mono<Long> addDriverToFree(String driverId) {
        return this.setOperations.add(FREE_DRIVERS, driverId);
    }
}

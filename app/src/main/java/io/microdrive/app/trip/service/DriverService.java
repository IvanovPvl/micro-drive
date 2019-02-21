package io.microdrive.app.trip.service;

import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.ReactiveSetOperations;

import java.util.Optional;

import io.microdrive.app.auth.domain.User;
import io.microdrive.app.auth.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class DriverService {

    private static final String FREE_DRIVERS = "free_drivers";

    private final UserRepository userRepository;
    private final ReactiveSetOperations<String, String> setOperations;

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

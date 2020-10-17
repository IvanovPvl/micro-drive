package io.microdrive.drivers.service;

import io.microdrive.core.dto.drivers.DriverResponse;
import io.microdrive.drivers.clients.AccountsClient;
import io.microdrive.drivers.errors.FreeDriverNotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveSetOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DriverService {
    private static final String FREE_DRIVERS = "free_drivers";

    private final ReactiveSetOperations<String, String> setOperations;
    private final AccountsClient accountClient;

    /**
     * Add driver to free set.
     *
     * @param driverId driverId
     * @return Mono
     */
    public Mono<Long> release(String driverId) {
        return setOperations.add(FREE_DRIVERS, driverId);
    }

    /**
     * Get free driver.
     *
     * @return Mono
     */
    public Mono<DriverResponse> request() {
        return setOperations.pop(FREE_DRIVERS)
            .flatMap(accountClient::getDriver)
            .switchIfEmpty(Mono.error(new FreeDriverNotFoundError()));
    }
}

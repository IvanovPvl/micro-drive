package io.microdrive.rides.service;

import io.microdrive.core.Result;
import io.microdrive.core.errors.RideNotFoundException;
import io.microdrive.core.types.rides.CreateRideRequest;
import io.microdrive.coresecurity.types.Principal;
import io.microdrive.rides.persistence.Ride;
import io.microdrive.rides.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static io.microdrive.core.Result.fail;
import static org.springframework.security.core.context.ReactiveSecurityContextHolder.getContext;
import static reactor.core.publisher.Mono.just;

@Service
@RequiredArgsConstructor
public class RideService {
    private final RideRepository repository;

    public Mono<Result<Ride>> create(CreateRideRequest request) {
        return getContext()
            .flatMap(securityContext -> {
                var principal = (Principal) securityContext.getAuthentication().getPrincipal();
                var ride = new Ride(request.routeId(), principal.getId(), request.driverId(), request.price());
                return repository.save(ride);
            })
            .map(Result::success)
            .onErrorResume(throwable -> just(fail(throwable)));
    }

    public Mono<Result<Ride>> updateStatus(String id, Ride.Status status) {
        return repository.findById(id)
            .flatMap(ride -> {
                ride.setStatus(status);
                return repository.save(ride);
            })
            .map(Result::success)
            .switchIfEmpty(just(fail(new RideNotFoundException(id))))
            .onErrorResume(throwable -> just(fail(throwable)));
    }
}

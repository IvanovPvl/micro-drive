package io.microdrive.trip.web;

import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import io.microdrive.auth.domain.User;
import io.microdrive.trip.service.TripService;
import io.microdrive.trip.service.DriverService;
import io.microdrive.trip.errors.TripNotFoundException;

@RestController
@RequestMapping("/trip/driver")
public class DriverTripController {

    private final TripService tripService;
    private final DriverService driverService;

    public DriverTripController(TripService tripService, DriverService driverService) {
        this.tripService = tripService;
        this.driverService = driverService;
    }

    @PostMapping("/start/{tripId}")
    public Mono<Void> startTrip(@PathVariable String tripId) {
        return this.tripService.startTrip(tripId)
                .flatMap(r -> !r ? Mono.error(new TripNotFoundException()) : Mono.empty());
    }

    @PostMapping("/finish/{tripId}")
    public Mono<Void> finishTrip(@PathVariable String tripId, @AuthenticationPrincipal User user) {
        return this.tripService.finishTrip(tripId)
                .flatMap(r -> !r ? Mono.error(new TripNotFoundException()) : Mono.empty())
                .flatMap(o -> this.driverService.addDriverToFree(user.getId()))
                .flatMap(l -> Mono.empty());
    }

    // TODO: check expected trips for driver
    // TODO: save points of active trip
}

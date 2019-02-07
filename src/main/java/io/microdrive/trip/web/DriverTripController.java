package io.microdrive.trip.web;

import reactor.core.publisher.Mono;
import io.microdrive.trip.domain.Point;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.microdrive.trip.repository.PointRepository;
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
    private final PointRepository pointRepository;

    public DriverTripController(TripService tripService, DriverService driverService, PointRepository pointRepository) {
        this.tripService = tripService;
        this.driverService = driverService;
        this.pointRepository = pointRepository;
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

    @PostMapping("/{tripId}/points")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createPoint(@PathVariable String tripId, @RequestBody io.microdrive.routing.Point point) {
        Point p = Point.builder()
                .tripId(tripId)
                .latitude(point.getLatitude())
                .longitude(point.getLongitude())
                .build();

        return this.pointRepository.save(p)
                .flatMap(pt -> Mono.empty());
    }

    // TODO: check expected trips for driver
}

package io.microdrive.trip.web;

import io.microdrive.pricing.dto.Info;
import io.microdrive.pricing.PriceCalculator;
import io.microdrive.trip.domain.TripInfo;
import io.microdrive.trip.errors.NoFreeDriversException;
import io.microdrive.trip.errors.TripNotFoundException;
import io.microdrive.trip.repository.PointRepository;
import io.microdrive.routing.RouteProvider;
import io.microdrive.trip.service.DriverService;
import io.microdrive.trip.service.TripService;
import io.microdrive.auth.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/trip")
public class TripController {

    private final RouteProvider routeProvider;
    private final PriceCalculator priceCalculator;
    private final PointRepository pointRepo;
    private final DriverService driverService;
    private final TripService tripService;

    public TripController(RouteProvider routeProvider,
                          PriceCalculator priceCalculator,
                          PointRepository pointRepo,
                          DriverService driverService,
                          TripService tripService) {
        this.routeProvider = routeProvider;
        this.priceCalculator = priceCalculator;
        this.pointRepo = pointRepo;
        this.driverService = driverService;
        this.tripService = tripService;
    }

    // Call by user
    @PostMapping("/info/{locations}")
    public Mono<TripInfo> getTripInfoByLocations(@PathVariable String locations, @AuthenticationPrincipal User user) {
        Mono<TripInfo> mono = Mono.from(routeProvider.calculateRoute(locations))
                .flatMap(route -> {
                    TripInfo tripInfo = TripInfo.builder()
                            .routeInfo(route)
                            .price(priceCalculator.calculate(new Info(route.getLengthInMeters())))
                            .userId(user.getId())
                            .status(TripInfo.Status.NEW)
                            .build();
                    return this.tripService.addTrip(tripInfo);
                });

        return Mono.from(mono);
    }

    // Call by user
    @PostMapping("/request/{tripId}")
    public Mono<User> requestTrip(@PathVariable String tripId) {
        // TODO: notify driver via WebSocket

        return this.driverService
                .getFreeDriver()
                .flatMap(op -> op.isPresent() ? Mono.just(op.get()) : Mono.error(new NoFreeDriversException()))
                .flatMap(u -> this.tripService.expectTrip(tripId, u.getId())
                        .flatMap(r -> r ? Mono.just(u) : Mono.error(new TripNotFoundException()))
                );
    }

    // Call by driver
    @PostMapping("/start/{tripId}")
    public void startTrip(@PathVariable String tripId) {
        // TODO: notify user via WebSocket
        this.tripService.startTrip(tripId);
    }
}

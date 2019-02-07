package io.microdrive.trip.web;

import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import io.microdrive.auth.domain.User;
import io.microdrive.pricing.dto.Info;
import io.microdrive.trip.domain.TripInfo;
import io.microdrive.routing.RouteProvider;
import io.microdrive.pricing.PriceCalculator;
import io.microdrive.trip.service.TripService;
import io.microdrive.trip.service.DriverService;
import io.microdrive.trip.errors.TripNotFoundException;
import io.microdrive.trip.errors.NoFreeDriversException;

@RestController
@RequestMapping("/trip/user")
public class UserTripController {

    private final RouteProvider routeProvider;
    private final PriceCalculator priceCalculator;
    private final DriverService driverService;
    private final TripService tripService;

    public UserTripController(RouteProvider routeProvider,
                              PriceCalculator priceCalculator,
                              DriverService driverService,
                              TripService tripService) {
        this.routeProvider = routeProvider;
        this.priceCalculator = priceCalculator;
        this.driverService = driverService;
        this.tripService = tripService;
    }

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

    @PostMapping("/request/{tripId}")
    public Mono<User> requestTrip(@PathVariable String tripId) {
        return this.driverService
                .getFreeDriver()
                .flatMap(op -> op.isPresent() ? Mono.just(op.get()) : Mono.error(new NoFreeDriversException()))
                .flatMap(u -> this.tripService.expectTrip(tripId, u.getId())
                        .flatMap(r -> r ? Mono.just(u) : Mono.error(new TripNotFoundException()))
                );
    }

}

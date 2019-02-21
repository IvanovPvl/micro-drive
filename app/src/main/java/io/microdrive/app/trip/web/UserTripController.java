package io.microdrive.app.trip.web;

import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import io.microdrive.app.auth.domain.User;
import io.microdrive.app.routing.RouteProvider;
import io.microdrive.app.trip.service.TripService;
import io.microdrive.app.trip.service.DriverService;
import io.microdrive.app.trip.pricing.PricingClient;
import io.microdrive.app.trip.domain.tripinfo.TripInfo;
import io.microdrive.app.trip.pricing.dto.PricingRequest;
import io.microdrive.app.trip.errors.TripNotFoundException;
import io.microdrive.app.trip.errors.NoFreeDriversException;
import io.microdrive.app.trip.domain.tripinfo.repository.TripInfoRepository;

@RestController
@RequestMapping("/trip/user")
@RequiredArgsConstructor
public class UserTripController {

    private final RouteProvider routeProvider;
    private final PricingClient pricingClient;
    private final DriverService driverService;
    private final TripService tripService;
    private final TripInfoRepository tripInfoRepository;

    @PostMapping("/info/{locations}")
    public Mono<TripInfo> getTripInfoByLocations(@PathVariable String locations, @AuthenticationPrincipal User user) {
        return this.routeProvider.calculateRoute(locations).flatMap(routeInfo ->
                this.pricingClient.calculate(new PricingRequest(routeInfo.getLengthInMeters()))
                        .flatMap(pricingResponse -> {
                            TripInfo tripInfo = TripInfo.builder()
                                    .routeInfo(routeInfo)
                                    .price(pricingResponse.getAmount())
                                    .userId(user.getId())
                                    .status(TripInfo.Status.NEW)
                                    .build();
                            return this.tripInfoRepository.save(tripInfo);
                        })
        ); // TODO: onError
    }

    @PostMapping("/{tripId}/request")
    public Mono<User> requestTrip(@PathVariable String tripId) {
        return this.driverService.getFreeDriver()
                .flatMap(op -> op.isPresent() ? Mono.just(op.get()) : Mono.error(new NoFreeDriversException()))
                .flatMap(u -> this.tripService.expectTrip(tripId, u.getId())
                        .flatMap(r -> r ? Mono.just(u) : Mono.error(new TripNotFoundException()))
                );
    }

}

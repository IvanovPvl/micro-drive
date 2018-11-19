package io.microdrive.trip.api.web;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.microdrive.trip.api.domain.Point;
import io.microdrive.trip.api.domain.TripInfo;
import io.microdrive.trip.routing.RouteProvider;
import io.microdrive.trip.pricing.PriceCalculator;
import io.microdrive.trip.api.data.PointRepository;
import io.microdrive.trip.api.data.TripInfoRepository;

@Slf4j
@RestController
@RequestMapping("/trip")
public class TripController {

    private final RouteProvider routeProvider;
    private final PriceCalculator priceCalculator;
    private final PointRepository pointRepo;
    private final TripInfoRepository tripInfoRepo;

    public TripController(RouteProvider routeProvider,
                          PriceCalculator priceCalculator,
                          PointRepository pointRepo,
                          TripInfoRepository tripInfoRepo) {
        this.routeProvider = routeProvider;
        this.priceCalculator = priceCalculator;
        this.pointRepo = pointRepo;
        this.tripInfoRepo = tripInfoRepo;
    }

    @PostMapping("/{locations}")
    public Mono<TripInfo> getTripInfoByLocations(@PathVariable String locations) {
        Mono<TripInfo> mono = Mono.from(routeProvider.calculateRoute(locations))
                .flatMap(route -> {
                    TripInfo tripInfo = TripInfo.builder()
                            .routeInfo(route)
                            .price(priceCalculator.calculate(route))
                            .build();
                    return tripInfoRepo.save(tripInfo);
                });

        return Mono.from(mono);
    }

    @GetMapping("/{tripId}")
    public Mono<TripInfo> getTripInfo(@PathVariable String tripId) {
        return tripInfoRepo.findById(tripId);
    }

    @GetMapping("/{tripId}/points")
    public Flux<Point> points(@PathVariable String tripId) {
        return pointRepo.findAllByTripId(tripId);
    }

    @PostMapping("/{tripId}/points")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Point> createPoint(@PathVariable String tripId, @RequestBody io.microdrive.trip.routing.Point point) {
        Point p = Point.builder()
                .latitude(point.getLatitude())
                .longitude(point.getLongitude())
                .tripId(tripId)
                .build();

        return pointRepo.save(p);
    }
}

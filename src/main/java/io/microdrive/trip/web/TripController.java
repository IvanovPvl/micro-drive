package io.microdrive.trip.web;

import io.microdrive.pricing.Info;
import io.microdrive.pricing.PriceCalculator;
import io.microdrive.trip.domain.TripInfo;
import io.microdrive.trip.repository.PointRepository;
import io.microdrive.trip.repository.TripInfoRepository;
import io.microdrive.trip.routing.RouteProvider;
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
                            .price(priceCalculator.calculate(new Info(route.getLengthInMeters())))
                            .build();
                    return tripInfoRepo.save(tripInfo);
                });

        return Mono.from(mono);
    }
}

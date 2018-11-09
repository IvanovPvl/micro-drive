package io.microdrive.trip.info;

import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.microdrive.trip.routing.RouteProvider;
import io.microdrive.trip.pricing.PriceCalculator;

@RestController
@RequestMapping("/info")
public class EndpointController {

    private final RouteProvider routeProvider;
    private final PriceCalculator priceCalculator;

    public EndpointController(RouteProvider routeProvider, PriceCalculator priceCalculator) {
        this.routeProvider = routeProvider;
        this.priceCalculator = priceCalculator;
    }

    @GetMapping("/{locations}")
    public Mono<TripInfo> index(@PathVariable String locations) {
        return Mono.from(routeProvider.calculateRoute(locations))
                .map(route -> new TripInfo(route, priceCalculator.calculate(route)));
    }
}

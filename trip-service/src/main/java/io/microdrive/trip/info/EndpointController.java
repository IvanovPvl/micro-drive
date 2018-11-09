package io.microdrive.trip.info;

import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.microdrive.trip.tomtom.model.Leg;
import io.microdrive.trip.tomtom.model.Route;
import io.microdrive.trip.tomtom.TomtomService;
import io.microdrive.trip.tomtom.model.Summary;
import io.microdrive.trip.pricing.PriceCalculator;
import io.microdrive.trip.tomtom.model.CalculateRouteResponse;

import java.util.List;

@RestController
@RequestMapping("/info")
public class EndpointController {

    private final TomtomService tomtomService;
    private final PriceCalculator priceCalculator;

    public EndpointController(TomtomService tomtomService, PriceCalculator priceCalculator) {
        this.tomtomService = tomtomService;
        this.priceCalculator = priceCalculator;
    }

    @GetMapping("/{locations}")
    public Mono<RouteInfo> index(@PathVariable String locations) {
        return tomtomService.calculateRoute(locations).map(this::convertResponse);
    }

    private RouteInfo convertResponse(CalculateRouteResponse response) {
        double price = priceCalculator.calculate(response);
        Route route = response.getRoutes().get(0);
        Summary summary = route.getSummary();
        List<Leg> legs = route.getLegs();

        return RouteInfo.builder()
                .lengthInMeters(summary.getLengthInMeters())
                .trafficDelayInSeconds(summary.getTrafficDelayInSeconds())
                .travelTimeInSeconds(summary.getTravelTimeInSeconds())
                .points(legs.get(0).getPoints())
                .price(price)
                .build();
    }
}

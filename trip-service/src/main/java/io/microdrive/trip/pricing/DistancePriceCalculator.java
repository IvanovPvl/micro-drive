package io.microdrive.trip.pricing;

import io.microdrive.trip.tomtom.model.Route;
import io.microdrive.trip.tomtom.model.CalculateRouteResponse;
import org.springframework.stereotype.Component;

@Component
public class DistancePriceCalculator implements PriceCalculator {

    private final PricingConfigProperties config;

    public DistancePriceCalculator(PricingConfigProperties config) {
        this.config = config;
    }

    @Override
    public double calculate(CalculateRouteResponse response) {
        Route route = response.getRoutes().get(0);
        return route.getSummary().getLengthInMeters() / 1000.0 * config.getPriceForKm();
    }
}

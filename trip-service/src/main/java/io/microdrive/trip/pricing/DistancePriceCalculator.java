package io.microdrive.trip.pricing;

import io.microdrive.trip.routing.RouteInfo;
import org.springframework.stereotype.Component;

@Component
public class DistancePriceCalculator implements PriceCalculator {

    private final PricingConfigProperties config;

    public DistancePriceCalculator(PricingConfigProperties config) {
        this.config = config;
    }

    @Override
    public double calculate(RouteInfo route) {
        return route.getLengthInMeters() / 1000.0 * config.getPriceForKm();
    }
}

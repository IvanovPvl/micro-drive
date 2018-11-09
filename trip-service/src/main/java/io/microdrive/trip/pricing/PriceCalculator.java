package io.microdrive.trip.pricing;

import io.microdrive.trip.routing.RouteInfo;

public interface PriceCalculator {
    double calculate(RouteInfo route);
}

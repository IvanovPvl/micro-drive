package io.microdrive.trip.pricing;

import io.microdrive.trip.tomtom.model.CalculateRouteResponse;

public interface PriceCalculator {
    double calculate(CalculateRouteResponse response);
}

package io.microdrive.pricing;

import io.microdrive.pricing.dto.Request;

public interface PriceCalculator {
    double calculate(Request info);
}

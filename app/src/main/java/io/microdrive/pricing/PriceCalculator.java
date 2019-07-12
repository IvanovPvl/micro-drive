package io.microdrive.pricing;

import io.microdrive.pricing.dto.Info;

public interface PriceCalculator {
    double calculate(Info info);
}

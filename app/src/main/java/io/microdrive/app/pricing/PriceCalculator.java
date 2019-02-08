package io.microdrive.app.pricing;

import io.microdrive.app.pricing.dto.Info;

public interface PriceCalculator {
    double calculate(Info info);
}

package io.microdrive.pricing.service;

import io.microdrive.core.types.pricing.PricingRequest;

public interface PriceService {
    double calculate(PricingRequest request);
}

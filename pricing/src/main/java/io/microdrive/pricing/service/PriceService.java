package io.microdrive.pricing.service;

import io.microdrive.core.dto.pricing.PricingRequest;

public interface PriceService {
    double calculate(PricingRequest request);
}

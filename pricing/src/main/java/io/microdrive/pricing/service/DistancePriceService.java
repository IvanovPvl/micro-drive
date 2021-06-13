package io.microdrive.pricing.service;

import io.microdrive.core.types.pricing.PricingRequest;
import io.microdrive.pricing.config.PricingConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class DistancePriceService implements PriceService {
    private final PricingConfigProperties config;

    @Override
    public double calculate(PricingRequest request) {
        return BigDecimal.valueOf(request.distanceInMeters() / 1000.0 * config.getPriceForKm())
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();
    }
}

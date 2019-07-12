package io.microdrive.pricing.service;

import io.microdrive.pricing.dto.RequestInfo;
import io.microdrive.pricing.config.PricingConfigProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DistancePriceService implements PriceService {

    private final PricingConfigProperties config;

    public DistancePriceService(PricingConfigProperties config) {
        this.config = config;
    }

    @Override
    public double calculate(RequestInfo info) {
        return new BigDecimal(info.getDistanceInMeters() / 1000.0 * config.getPriceForKm())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

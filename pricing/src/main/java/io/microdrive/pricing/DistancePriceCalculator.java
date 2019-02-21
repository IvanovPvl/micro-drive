package io.microdrive.pricing;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import io.microdrive.pricing.dto.Request;
import io.microdrive.pricing.config.PricingConfigProperties;

@Component
public class DistancePriceCalculator implements PriceCalculator {

    private final PricingConfigProperties config;

    public DistancePriceCalculator(PricingConfigProperties config) {
        this.config = config;
    }

    @Override
    public double calculate(Request info) {
        return new BigDecimal(info.getLength() / 1000.0 * config.getPriceForKm())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

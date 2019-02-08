package io.microdrive.app.pricing;

import io.microdrive.app.pricing.config.PricingConfigProperties;
import io.microdrive.app.pricing.dto.Info;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DistancePriceCalculator implements PriceCalculator {

    private final PricingConfigProperties config;

    public DistancePriceCalculator(PricingConfigProperties config) {
        this.config = config;
    }

    @Override
    public double calculate(Info info) {
        return new BigDecimal(info.getLength() / 1000.0 * config.getPriceForKm())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

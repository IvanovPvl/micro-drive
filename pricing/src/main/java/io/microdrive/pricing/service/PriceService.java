package io.microdrive.pricing.service;

import io.microdrive.pricing.dto.RequestInfo;

public interface PriceService {
    double calculate(RequestInfo info);
}

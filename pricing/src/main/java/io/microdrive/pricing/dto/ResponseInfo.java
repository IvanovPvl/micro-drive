package io.microdrive.pricing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class ResponseInfo {
    private double price;
}

package io.microdrive.pricing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
public class RequestInfo {
    private int distanceInMeters;
}

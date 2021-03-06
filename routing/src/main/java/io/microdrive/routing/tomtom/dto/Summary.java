package io.microdrive.routing.tomtom.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Summary {
    private int lengthInMeters;
    private int travelTimeInSeconds;
    private int trafficDelayInSeconds;
    private String departureTime;
}

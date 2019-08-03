package io.microdrive.routing.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RouteInfo {
    private List<Point> points;
    private int lengthInMeters;
    private int travelTimeInSeconds;
    private int trafficDelayInSeconds;
}

package io.microdrive.routing;

import lombok.Data;
import lombok.Builder;

import java.util.List;

@Data
@Builder
public class RouteInfo {
    private List<Point> points;
    private int lengthInMeters;
    private int travelTimeInSeconds;
    private int trafficDelayInSeconds;
}

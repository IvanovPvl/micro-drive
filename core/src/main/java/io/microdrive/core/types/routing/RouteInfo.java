package io.microdrive.core.types.routing;

import java.util.List;

public record RouteInfo(
    List<Point> points,
    int lengthInMeters,
    int travelTimeInSeconds,
    int trafficDelayInSeconds
) {}

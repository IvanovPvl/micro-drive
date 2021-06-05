package io.microdrive.core.types.routing;

import java.util.List;

public record RouteResponse(
    String id,
    List<Point> points,
    int lengthInMeters,
    int travelTimeInSeconds,
    int trafficDelayInSeconds
) {}

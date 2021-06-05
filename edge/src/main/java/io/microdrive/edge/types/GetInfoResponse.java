package io.microdrive.edge.types;

import io.microdrive.core.types.routing.RouteResponse;

public record GetInfoResponse(RouteResponse route, double price) {}

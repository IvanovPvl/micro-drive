package io.microdrive.core.types.rides;

public record CreateRideRequest(String routeId, String driverId, double price) {}

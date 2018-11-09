package io.microdrive.trip.info;

import java.util.List;

import lombok.Builder;
import lombok.Data;

import io.microdrive.trip.tomtom.model.Point;


@Data
@Builder
public class RouteInfo {
    private List<Point> points;
    private int lengthInMeters;
    private int travelTimeInSeconds;
    private int trafficDelayInSeconds;
    private double price;
}

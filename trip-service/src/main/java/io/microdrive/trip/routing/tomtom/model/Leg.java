package io.microdrive.trip.routing.tomtom.model;

import io.microdrive.trip.routing.Point;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Leg {
    private Summary summary;
    private List<Point> points;
}

package io.microdrive.routing.tomtom.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.microdrive.routing.Point;

@Data
@NoArgsConstructor
public class Leg {
    private Summary summary;
    private List<Point> points;
}

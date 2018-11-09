package io.microdrive.trip.tomtom.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Leg {
    private Summary summary;
    private List<Point> points;
}

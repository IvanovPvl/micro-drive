package io.microdrive.routing.tomtom.dto;

import io.microdrive.core.types.routing.Point;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Leg {
    private Summary summary;
    private List<Point> points;
}

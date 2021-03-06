package io.microdrive.routing.tomtom.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Route {
    private Summary summary;
    private List<Leg> legs;
    private List<Section> sections;
}

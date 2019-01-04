package io.microdrive.trip.routing.tomtom.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Section {
    private int startPointIndex;
    private int endPointIndex;
    private String sectionType;
    private String travelMode;
}

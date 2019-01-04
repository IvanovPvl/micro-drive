package io.microdrive.trip.routing.tomtom.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CalculateRouteResponse {
    private String copyright;
    private String formatVersion;
    private String privacy;
    private List<Route> routes;
}

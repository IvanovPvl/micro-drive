package io.microdrive.trip.api.domain;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.microdrive.trip.routing.RouteInfo;

@Data
@Builder
@AllArgsConstructor
public class TripInfo {
    @JsonProperty("route")
    private RouteInfo routeInfo;

    private double price;
}

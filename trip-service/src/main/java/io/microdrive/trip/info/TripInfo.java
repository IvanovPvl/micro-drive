package io.microdrive.trip.info;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.microdrive.trip.routing.RouteInfo;

@Data
@Builder
@AllArgsConstructor
class TripInfo {
    @JsonProperty("route")
    private RouteInfo routeInfo;

    private double price;
}

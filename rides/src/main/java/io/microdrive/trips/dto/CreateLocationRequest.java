package io.microdrive.trips.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateLocationRequest {
    private double latitude;
    private double longitude;
    private String tripId;
}

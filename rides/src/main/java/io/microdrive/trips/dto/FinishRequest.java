package io.microdrive.trips.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FinishRequest {
    private String tripId;
}

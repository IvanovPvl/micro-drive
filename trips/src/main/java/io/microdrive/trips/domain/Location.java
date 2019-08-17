package io.microdrive.trips.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
@Document("locations")
@AllArgsConstructor
public class Location {
    @Id private String id;
    private double latitude;
    private double longitude;
    private String tripId;
    @CreatedDate Date createdAt;
}

package io.microdrive.trip.api.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document("points")
public class Point {
    @Id private String id;
    private double latitude;
    private double longitude;
    private String tripId;
    @CreatedDate Date createdAt;
}

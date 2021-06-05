package io.microdrive.routing.persistence;

import lombok.Data;
import lombok.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("routes")
public class Route {
    @MongoId(value = FieldType.OBJECT_ID) private String id;
    private List<Point> points;
    private int lengthInMeters;
    private int travelTimeInSeconds;
    private int trafficDelayInSeconds;
    @CreatedDate private LocalDateTime createdAt;

    @Value
    public static class Point {
        double latitude;
        double longitude;
    }
}

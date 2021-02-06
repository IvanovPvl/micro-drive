package io.microdrive.trips.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Builder
@Document("locations")
@AllArgsConstructor
public class Location {
    @MongoId(value = FieldType.OBJECT_ID) private String id;
    private double latitude;
    private double longitude;
    private String tripId;
    @CreatedDate LocalDateTime createdAt;
}

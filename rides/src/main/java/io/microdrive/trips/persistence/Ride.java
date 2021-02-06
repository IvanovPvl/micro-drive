package io.microdrive.trips.persistence;

import io.microdrive.core.dto.routing.RouteInfo;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Builder
@Document("rides")
public class Ride {
    @MongoId(value = FieldType.OBJECT_ID) private String id;

    private double price;
    private String userId;
    private String driverId;
    private Status status;

    @CreatedDate private LocalDateTime createdAt;
    @LastModifiedDate private LocalDateTime updatedAt;

    public enum Status {
        NEW,
        CLAIMED,
        IN_PROGRESS,
        FINISHED,
    }
}

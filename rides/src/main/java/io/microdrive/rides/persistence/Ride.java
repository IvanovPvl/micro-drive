package io.microdrive.rides.persistence;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Document("rides")
public class Ride {
    @MongoId(value = FieldType.OBJECT_ID) private String id;
    private String routeId;
    private double price;
    private String userId;
    private String driverId;
    private Status status = Status.NEW;

    @CreatedDate private LocalDateTime createdAt = LocalDateTime.now();
    @LastModifiedDate private LocalDateTime updatedAt = LocalDateTime.now();

    public Ride(String routeId, String userId, String driverId, double price) {
        this.routeId = routeId;
        this.userId = userId;
        this.driverId = driverId;
        this.price = price;
    }

    public enum Status {
        NEW,
        CLAIMED,
        IN_PROGRESS,
        FINISHED,
    }
}

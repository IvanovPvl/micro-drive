package io.microdrive.trip.domain;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import javax.persistence.Id;

@Data
@Builder
@Document("points")
@AllArgsConstructor
public class Point {

    @Id
    private String id;
    private double latitude;
    private double longitude;
    private String tripId;
    @CreatedDate
    Date createdAt;
}

package io.microdrive.app.trip.domain.tripinfo;

import lombok.Data;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import javax.persistence.Id;

import io.microdrive.app.routing.RouteInfo;

@Data
@Builder
@Document("tripsInfo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripInfo {
    @Id private String id;

    @JsonProperty("route")
    private RouteInfo routeInfo;

    private double price;
    private String userId;
    private String driverId;
    private Status status;

    @CreatedDate private Date createdAt;

    @LastModifiedDate private Date updatedAt;

    public enum Status {
        NEW,
        EXPECTED,
        IN_PROGRESS,
        FINISHED,
    }
}

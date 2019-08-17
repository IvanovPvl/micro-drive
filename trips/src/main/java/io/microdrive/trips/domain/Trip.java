package io.microdrive.trips.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.microdrive.core.dto.routing.RouteInfo;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
@Document("tripsInfo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Trip {
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
        CLAIMED,
        IN_PROGRESS,
        FINISHED,
    }
}

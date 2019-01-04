package io.microdrive.trip.domain;

import lombok.Data;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import javax.persistence.Id;

import io.microdrive.trip.routing.RouteInfo;

@Data
@Builder
@Document("tripsInfo")
public class TripInfo {
    @Id
    private String id;

    @JsonProperty("route")
    private RouteInfo routeInfo;

    private double price;

    @CreatedDate
    private Date createdAt;
}

package io.microdrive.trip.api.domain;

import lombok.Data;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

import io.microdrive.trip.routing.RouteInfo;

@Data
@Builder
@Document("tripsInfo")
public class TripInfo {
    @Id private String id;

    @JsonProperty("route")
    private RouteInfo routeInfo;

    private double price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SZ")
    @CreatedDate private Date createdAt;
}

package io.microdrive.trip.api;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trip")
public class TripController {

    private final PointRepository pointRepo;

    public TripController(PointRepository pointRepo) {
        this.pointRepo = pointRepo;
    }

    @GetMapping("/{tripId}/points")
    public Flux<Point> show(@PathVariable String tripId) {
        return pointRepo.findAllByTripId(tripId);
    }

    @PostMapping("/{tripId}/points")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Point> create(@PathVariable String tripId, @RequestBody io.microdrive.trip.routing.Point point) {
        Point p = Point.builder()
                .latitude(point.getLatitude())
                .longitude(point.getLongitude())
                .tripId(tripId)
                .build();

        return pointRepo.save(p);
    }
}

package io.microdrive.trip.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.microdrive.trip.service.TripService;

@RestController
@RequestMapping("/trip/driver")
public class DriverTripController {

    private final TripService tripService;

    public DriverTripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/start/{tripId}")
    public void startTrip(@PathVariable String tripId) {
        this.tripService.startTrip(tripId);
    }
}

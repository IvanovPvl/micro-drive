package io.microdrive.core.errors;

public class RideNotFoundException extends ResourceNotFoundException {
    public RideNotFoundException(String id) {
        super(String.format("Ride with id='%s' not found", id));
    }
}

package io.microdrive.trip.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No free drivers")
public class TripNotFoundException extends RuntimeException {}

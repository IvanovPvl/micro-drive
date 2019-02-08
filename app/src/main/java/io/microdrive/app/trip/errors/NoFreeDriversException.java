package io.microdrive.app.trip.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No free drivers")
public class NoFreeDriversException extends RuntimeException {}

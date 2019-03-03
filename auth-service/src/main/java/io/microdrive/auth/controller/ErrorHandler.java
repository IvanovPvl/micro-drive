package io.microdrive.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.microdrive.auth.dto.ErrorResponse;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handle(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }
}

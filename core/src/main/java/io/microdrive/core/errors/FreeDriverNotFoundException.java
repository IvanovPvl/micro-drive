package io.microdrive.core.errors;

public class FreeDriverNotFoundException extends ResourceNotFoundException {
    public FreeDriverNotFoundException() {
        super("Free drivers not found.");
    }
}
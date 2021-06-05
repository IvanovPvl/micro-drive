package io.microdrive.core.errors;

public class FreeDriverNotFoundException extends RuntimeException {
    public FreeDriverNotFoundException() {
        super("Free drivers not found.");
    }
}
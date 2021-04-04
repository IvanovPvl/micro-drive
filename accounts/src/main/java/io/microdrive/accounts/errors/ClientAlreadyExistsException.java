package io.microdrive.accounts.errors;

public class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException(String phoneNumber) {
        super(String.format("Client with %s phoneNumber already exists", phoneNumber));
    }
}

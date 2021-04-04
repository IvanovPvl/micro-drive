package io.microdrive.accounts.errors;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException() {
        super("Client with this phoneNumber and password not found");
    }
}

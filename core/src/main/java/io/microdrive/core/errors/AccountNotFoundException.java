package io.microdrive.core.errors;

public class AccountNotFoundException extends ResourceNotFoundException {
    public AccountNotFoundException(String phoneNumber) {
        super(String.format("Account with phoneNumber='%s' not found", phoneNumber));
    }
}

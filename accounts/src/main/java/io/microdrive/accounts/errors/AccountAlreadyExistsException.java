package io.microdrive.accounts.errors;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String phoneNumber) {
        super(String.format("Account with phoneNumber='%s' already exists", phoneNumber));
    }
}

package io.microdrive.accounts.errors;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String phoneNumber) {
        super(String.format("Account with phoneNumber='%s' not found", phoneNumber));
    }
}

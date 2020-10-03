package io.microdrive.accounts.errors;

public class AccountNotFound extends RuntimeException {
    public AccountNotFound(String phoneNumber) {
        super(String.format("Account with phoneNumber: '%s' not found.", phoneNumber));
    }

    public ResponseError toResponseError() {
        return new ResponseError(getMessage());
    }
}

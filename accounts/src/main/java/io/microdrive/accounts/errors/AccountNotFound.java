package io.microdrive.accounts.errors;

import io.microdrive.core.dto.errors.ResponseError;

public class AccountNotFound extends RuntimeException {
    public AccountNotFound(String phoneNumber) {
        super(String.format("Account with phoneNumber: '%s' not found.", phoneNumber));
    }

    public ResponseError toResponseError() {
        return new ResponseError(getMessage());
    }
}

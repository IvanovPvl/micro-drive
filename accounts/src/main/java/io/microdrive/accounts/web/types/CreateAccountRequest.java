package io.microdrive.accounts.web.types;

import io.microdrive.accounts.persistence.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAccountRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private Account.Role role;
    private CreateCarRequest car;

    @Data
    @NoArgsConstructor
    public static class CreateCarRequest {
        private String mark;
        private String number;
        private String color;
    }
}

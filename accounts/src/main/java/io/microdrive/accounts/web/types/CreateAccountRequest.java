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

    public Account toClientAccount() {
        var account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setPhoneNumber(phoneNumber);
        account.setClient(true);
        return account;
    }

    public Account toDriverAccount() {
        var account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setPhoneNumber(phoneNumber);
        account.setDriver(true);
        return account;
    }
}

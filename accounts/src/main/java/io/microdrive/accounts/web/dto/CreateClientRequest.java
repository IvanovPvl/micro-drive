package io.microdrive.accounts.web.dto;

import io.microdrive.accounts.persistence.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateClientRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;

    public Account toAccount() {
        var account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setPhoneNumber(phoneNumber);
        account.setClient(true);
        return account;
    }
}

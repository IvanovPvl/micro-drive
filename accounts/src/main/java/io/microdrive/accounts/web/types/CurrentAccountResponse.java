package io.microdrive.accounts.web.types;

import io.microdrive.accounts.persistence.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrentAccountResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public CurrentAccountResponse(Account account) {
        this.id = account.getId();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.phoneNumber = account.getPhoneNumber();
    }
}

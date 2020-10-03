package io.microdrive.accounts.web.dto;

import io.microdrive.accounts.persistence.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean isDriver;
    private boolean isClient;

    public static AccountResponse fromAccount(Account account) {
        var response = new AccountResponse();
        response.setId(account.getId());
        response.setFirstName(account.getFirstName());
        response.setLastName(account.getLastName());
        response.setPhoneNumber(account.getPhoneNumber());
        response.setDriver(account.isDriver());
        response.setClient(account.isClient());
        return response;
    }
}

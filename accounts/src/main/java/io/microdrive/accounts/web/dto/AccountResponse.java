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
}

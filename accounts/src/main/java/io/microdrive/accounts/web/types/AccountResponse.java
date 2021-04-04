package io.microdrive.accounts.web.types;

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

package io.microdrive.accounts.web.types.client;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateClientRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
}

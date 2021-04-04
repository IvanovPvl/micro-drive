package io.microdrive.accounts.web.types;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckPasswordRequest {
    private String phoneNumber;
    private String password;
}

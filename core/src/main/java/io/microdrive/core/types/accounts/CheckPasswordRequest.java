package io.microdrive.core.types.accounts;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckPasswordRequest {
    private String phoneNumber;
    private String password;
}

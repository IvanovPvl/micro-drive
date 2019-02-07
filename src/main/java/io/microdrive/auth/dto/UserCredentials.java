package io.microdrive.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCredentials {
    private String username;
    private String password;
}

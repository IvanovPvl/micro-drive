package io.microdrive.core.types.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckPasswordResponse {
    private String accountId;
    private String role;
}

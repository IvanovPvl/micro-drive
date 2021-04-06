package io.microdrive.edge.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {
    private String accessToken;
    private Long issuedAt;
    private Long expiredAt;
}

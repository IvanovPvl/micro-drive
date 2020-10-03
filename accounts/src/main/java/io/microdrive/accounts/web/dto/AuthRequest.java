package io.microdrive.accounts.web.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String phoneNumber;
    private String password;
}

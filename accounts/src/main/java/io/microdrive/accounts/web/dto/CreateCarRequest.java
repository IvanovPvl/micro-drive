package io.microdrive.accounts.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCarRequest {
    private String mark;
    private String number;
    private String color;
}

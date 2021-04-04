package io.microdrive.accounts.web.types;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCarRequest {
    private String mark;
    private String number;
    private String color;
}

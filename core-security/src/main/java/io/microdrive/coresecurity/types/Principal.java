package io.microdrive.coresecurity.types;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Principal {
    private String id;
    private String role;
}

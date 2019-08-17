package io.microdrive.core.dto.drivers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String id;
    private String firstName;
    private String lastName;
    private Car car;
}

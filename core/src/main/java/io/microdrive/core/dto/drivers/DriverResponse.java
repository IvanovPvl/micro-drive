package io.microdrive.core.dto.drivers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNUmber;
    private Car car;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Car {
        private String id;
        private String mark;
        private String number;
        private String color;
    }
}

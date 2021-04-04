package io.microdrive.accounts.web.types.driver;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateDriverRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private Car car;

    @Data
    @NoArgsConstructor
    public static class Car {
        private String mark;
        private String number;
        private String color;
    }
}

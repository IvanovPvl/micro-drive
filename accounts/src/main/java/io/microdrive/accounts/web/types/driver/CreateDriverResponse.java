package io.microdrive.accounts.web.types.driver;

import io.microdrive.accounts.persistence.Driver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateDriverResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private CreateDriverResponse.Car car;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Car {
        private String id;
        private String mark;
        private String number;
        private String color;
    }

    public CreateDriverResponse(Driver driver) {
        this.id = driver.getId();
        this.firstName = driver.getFirstName();
        this.lastName = driver.getLastName();
        this.phoneNumber = driver.getPhoneNumber();
        var car = driver.getCar();
        this.car = Car.builder()
            .id(car.getId())
            .mark(car.getMark())
            .number(car.getNumber())
            .color(car.getColor())
            .build();

    }
}

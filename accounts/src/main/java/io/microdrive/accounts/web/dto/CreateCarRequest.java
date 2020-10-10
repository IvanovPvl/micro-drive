package io.microdrive.accounts.web.dto;

import io.microdrive.accounts.persistence.Car;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCarRequest {
    private String mark;
    private String number;
    private String color;
    private String accountId;

    public Car toCar() {
        var car = new Car();
        car.setAccountId(accountId);
        car.setColor(color);
        car.setMark(mark);
        car.setNumber(number);
        return car;
    }
}

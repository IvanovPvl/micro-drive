package io.microdrive.accounts.persistence;

import io.microdrive.accounts.web.types.driver.CreateDriverRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("cars")
public class Car {
    @Id private String id;
    private String mark;
    private String number;
    private String color;

    public Car(CreateDriverRequest.Car car) {
        this.mark = car.getMark();
        this.number = car.getNumber();
        this.color = car.getColor();
    }
}

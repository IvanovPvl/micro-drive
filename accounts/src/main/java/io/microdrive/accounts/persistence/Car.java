package io.microdrive.accounts.persistence;

import io.microdrive.accounts.web.types.CreateAccountRequest;
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
    private String accountId;

    public Car(CreateAccountRequest.CreateCarRequest request) {
        this.mark = request.getMark();
        this.number = request.getNumber();
        this.color = request.getColor();
    }
}

package io.microdrive.accounts.persistence;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("cars")
public class Car {
    @MongoId private String id;
    private String mark;
    private String number;
    private String color;
    private String accountId;
}

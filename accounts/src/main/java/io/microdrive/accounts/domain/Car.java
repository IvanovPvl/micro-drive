package io.microdrive.accounts.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cars")
public class Car {
    @Id private String id;
    private String mark;
    private String number;
}

package io.microdrive.accounts.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("accounts")
public class Account {
    @Id private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    @DBRef Car car;
}

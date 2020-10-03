package io.microdrive.accounts.persistence;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("accounts")
public class Account {
    @MongoId private String id;
    private String firstName;
    private String lastName;
    private String password;

    @Indexed(unique = true)
    private String phoneNumber;

    private boolean isDriver = false;
    private boolean isClient = false;
}

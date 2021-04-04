package io.microdrive.accounts.persistence;

import io.microdrive.accounts.web.types.CreateAccountRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("accounts")
@NoArgsConstructor
public class Account {
    @Id private String id;
    private String firstName;
    private String lastName;
    private String password;

    @Indexed(unique = true)
    private String phoneNumber;

    private Role role;

    public enum Role {
        CLIENT,
        DRIVER
    }

    public Account(CreateAccountRequest request) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.phoneNumber = request.getPhoneNumber();
        this.role = request.getRole();
    }
}

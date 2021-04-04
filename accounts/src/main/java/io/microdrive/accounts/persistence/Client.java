package io.microdrive.accounts.persistence;

import io.microdrive.accounts.web.types.client.CreateClientRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("clients")
@NoArgsConstructor
public class Client {
    @Id private String id;
    private String firstName;
    private String lastName;
    private String password;
    @Indexed(unique = true) private String phoneNumber;

    public Client(CreateClientRequest request) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.phoneNumber = request.getPhoneNumber();
    }
}

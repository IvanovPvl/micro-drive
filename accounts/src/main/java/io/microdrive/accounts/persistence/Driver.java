package io.microdrive.accounts.persistence;

import io.microdrive.accounts.web.types.driver.CreateDriverRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("drivers")
public class Driver {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    @Indexed(unique = true) private String phoneNumber;
    @DBRef private Car car;

    public Driver(CreateDriverRequest request) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.phoneNumber = request.getPhoneNumber();
    }
}

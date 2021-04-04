package io.microdrive.accounts.web.types.client;

import io.microdrive.accounts.persistence.Client;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateClientResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public CreateClientResponse(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.phoneNumber = client.getPhoneNumber();
    }
}

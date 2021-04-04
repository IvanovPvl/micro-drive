package io.microdrive.accounts.web.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.microdrive.accounts.persistence.Account;
import io.microdrive.accounts.persistence.Car;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAccountResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Account.Role role;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CreateAccountResponse.CreateCarResponse car;

    @Data
    @NoArgsConstructor
    public static class CreateCarResponse {
        private String id;
        private String mark;
        private String number;
        private String color;

        public CreateCarResponse(Car car) {
            this.id = car.getId();
            this.mark = car.getMark();
            this.number = car.getNumber();
            this.color = car.getColor();
        }
    }

    public CreateAccountResponse(Account account) {
        this.id = account.getId();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.phoneNumber = account.getPhoneNumber();
        this.role = account.getRole();
    }

    public CreateAccountResponse(Account account, Car car) {
        this.id = account.getId();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.phoneNumber = account.getPhoneNumber();
        this.role = account.getRole();
        this.car = new CreateCarResponse(car);
    }
}

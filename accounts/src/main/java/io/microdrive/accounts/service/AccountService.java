package io.microdrive.accounts.service;

import io.microdrive.accounts.persistence.Account;
import io.microdrive.accounts.repository.AccountRepository;
import io.microdrive.accounts.web.dto.CreateAccountRequest;
import io.microdrive.core.dto.drivers.DriverResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final CarService carService;

    public Mono<Account> createClient(CreateAccountRequest request) {
         var account = request.toClientAccount();
         account.setPassword(passwordEncoder.encode(request.getPassword()));
         return accountRepository.save(account);
    }

    public Mono<Account> createDriver(CreateAccountRequest request) {
        var account = request.toDriverAccount();
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        return accountRepository.save(account);
    }

    public Mono<DriverResponse> findDriverById(String id) {
        return Mono.zip(accountRepository.findById(id), carService.findByAccountId(id), (account, car) -> {
            var response = new DriverResponse();
            response.setId(account.getId());
            response.setFirstName(account.getFirstName());
            response.setLastName(account.getLastName());
            response.setPhoneNumber(account.getPhoneNumber());
            var c = new DriverResponse.Car();
            c.setId(car.getId());
            c.setColor(car.getColor());
            c.setMark(car.getMark());
            c.setNumber(car.getNumber());
            response.setCar(c);
            return response;
        });
    }
}

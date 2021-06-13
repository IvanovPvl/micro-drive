package io.microdrive.accounts.service;

import io.microdrive.core.Result;
import io.microdrive.core.errors.AccountAlreadyExistsException;
import io.microdrive.accounts.persistence.Account;
import io.microdrive.accounts.repository.AccountRepository;
import io.microdrive.accounts.web.types.CreateAccountRequest;
import io.microdrive.accounts.web.types.CreateAccountResponse;
import io.microdrive.accounts.web.types.CurrentAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static io.microdrive.core.Result.fail;
import static io.microdrive.core.Result.success;
import static reactor.core.publisher.Mono.just;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final CarService carService;
    private final PasswordEncoder passwordEncoder;

    public Mono<Result<CreateAccountResponse>> create(CreateAccountRequest request) {
        var account = new Account(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        return accountRepository.save(account)
            .flatMap(a -> {
                if (request.getRole() == Account.Role.DRIVER) {
                    return carService.create(request.getCar(), a.getId())
                        .flatMap(car -> just(success(new CreateAccountResponse(a, car))));
                }

                return just(success(new CreateAccountResponse(a)));
            })
            .onErrorResume(throwable -> onError(throwable, request.getPhoneNumber()));
    }

    public Mono<Account> findByPhoneNumber(String phoneNumber) {
        return accountRepository.findByPhoneNumber(phoneNumber);
    }

    public Mono<CurrentAccountResponse> current(String id) {
        return accountRepository.findById(id)
            .map(CurrentAccountResponse::new);
    }

    private Mono<Result<CreateAccountResponse>> onError(Throwable throwable, String phoneNumber) {
        if (throwable instanceof DuplicateKeyException) {
            return just(fail(new AccountAlreadyExistsException(phoneNumber)));
        }
        return just(fail(throwable));
    }
}

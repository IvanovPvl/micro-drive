package io.microdrive.accounts.service;

import io.microdrive.accounts.Result;
import io.microdrive.accounts.errors.AccountAlreadyExistsException;
import io.microdrive.accounts.persistence.Account;
import io.microdrive.accounts.repository.AccountRepository;
import io.microdrive.accounts.web.types.CreateAccountRequest;
import io.microdrive.accounts.web.types.CreateAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final CarService carService;
    private final PasswordEncoder passwordEncoder;

    public Mono<Result<CreateAccountResponse>> create(CreateAccountRequest request) {
        var account = new Account(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getRole() == Account.Role.DRIVER) {
            return carService.create(request.getCar())
                .flatMap(car -> {
                    account.setCar(car);
                    return accountRepository.save(account);
                })
                .flatMap(a -> Mono.just(Result.success(new CreateAccountResponse(a))))
                .onErrorResume(throwable -> onError(throwable, request.getPhoneNumber()));
        }

        return accountRepository.save(account)
            .flatMap(a -> Mono.just(Result.success(new CreateAccountResponse(a))))
            .onErrorResume(throwable -> onError(throwable, request.getPhoneNumber()));
    }

    private Mono<Result<CreateAccountResponse>> onError(Throwable throwable, String phoneNumber) {
        if (throwable instanceof DuplicateKeyException) {
            return Mono.just(Result.fail(new AccountAlreadyExistsException(phoneNumber)));
        }
        return Mono.just(Result.fail(throwable));
    }
}

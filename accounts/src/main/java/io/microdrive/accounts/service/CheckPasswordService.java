package io.microdrive.accounts.service;

import io.microdrive.core.Result;
import io.microdrive.core.errors.AccountNotFoundException;
import io.microdrive.core.types.accounts.CheckPasswordRequest;
import io.microdrive.core.types.accounts.CheckPasswordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static io.microdrive.core.Result.fail;
import static io.microdrive.core.Result.success;
import static reactor.core.publisher.Mono.just;

@Service
@RequiredArgsConstructor
public class CheckPasswordService {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    public Mono<Result<?>> check(CheckPasswordRequest request) {
        return accountService.findByPhoneNumber(request.phoneNumber())
            .flatMap(account -> {
                if (!passwordEncoder.matches(request.password(), account.getPassword())) {
                    return just(fail(new AccountNotFoundException(request.phoneNumber())));
                }
                return just(success(new CheckPasswordResponse(account.getId(), account.getRole().name())));
            })
            .switchIfEmpty(just(fail(new AccountNotFoundException(request.phoneNumber()))));
    }
}

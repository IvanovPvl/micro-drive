package io.microdrive.accounts.service;

import io.microdrive.accounts.Result;
import io.microdrive.accounts.errors.AccountNotFoundException;
import io.microdrive.accounts.web.types.CheckPasswordRequest;
import io.microdrive.accounts.web.types.CheckPasswordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CheckPasswordService {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    public Mono<Result<?>> check(CheckPasswordRequest request) {
        return accountService.findByPhoneNumber(request.getPhoneNumber())
            .flatMap(account -> {
                if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
                    return Mono.just(Result.fail(new AccountNotFoundException(request.getPhoneNumber())));
                }
                return Mono.just(Result.success(new CheckPasswordResponse(true)));
            })
            .switchIfEmpty(Mono.just(Result.fail(new AccountNotFoundException(request.getPhoneNumber()))));
    }
}

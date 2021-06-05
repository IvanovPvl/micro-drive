package io.microdrive.accounts.service;

import io.microdrive.core.Result;
import io.microdrive.core.errors.AccountNotFoundException;
import io.microdrive.core.types.accounts.CheckPasswordRequest;
import io.microdrive.core.types.accounts.CheckPasswordResponse;
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
                return Mono.just(Result.success(new CheckPasswordResponse(account.getId(), account.getRole().name())));
            })
            .switchIfEmpty(Mono.just(Result.fail(new AccountNotFoundException(request.getPhoneNumber()))));
    }
}

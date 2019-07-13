package io.microdrive.accounts.service;

import io.microdrive.accounts.domain.Account;
import io.microdrive.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Mono<Account> findDriver(String id) {
        return accountRepository.findByIdAndRole(id, "driver");
    }

}

package io.microdrive.accounts.service;

import io.microdrive.accounts.repository.AccountRepository;
import io.microdrive.accounts.web.dto.AccountResponse;
import io.microdrive.accounts.web.dto.CreateClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<AccountResponse> create(CreateClientRequest request) {
         var account = request.toAccount();
         account.setPassword(passwordEncoder.encode(request.getPassword()));
         return accountRepository.save(account)
             .map(AccountResponse::fromAccount);
    }
}

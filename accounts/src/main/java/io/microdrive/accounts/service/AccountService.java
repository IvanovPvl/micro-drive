package io.microdrive.accounts.service;

import io.microdrive.accounts.domain.Account;
import io.microdrive.accounts.errors.UsernameAlreadyExistsError;
import io.microdrive.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.github.ivpal.Result;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Account> findDriver(String id) {
        return accountRepository.findByIdAndRole(id, "driver");
    }

    public Result<Account, UsernameAlreadyExistsError> create(Account account) {
        val username = account.getUsername();
        if (accountRepository.findByUsername(username).isPresent()) {
            return Result.failure(new UsernameAlreadyExistsError(username));
        }

        val password = passwordEncoder.encode(account.getPassword());
        account.setPassword(password);
        val createdAccount = accountRepository.save(account);
        return Result.success(createdAccount);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return accountRepository.findByUsername(username)
                .map(a -> (UserDetails) a)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}

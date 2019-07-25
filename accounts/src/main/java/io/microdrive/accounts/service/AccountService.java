package io.microdrive.accounts.service;

import io.microdrive.accounts.domain.Account;
import io.microdrive.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Account> findDriver(String id) {
        return accountRepository.findByIdAndRole(id, "driver");
    }

    public void create(Account account) {
        val password = passwordEncoder.encode(account.getPassword());
        account.setPassword(password);
        accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return accountRepository.findByUsername(username)
                .map(a -> (UserDetails) a)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}

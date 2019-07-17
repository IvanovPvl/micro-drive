package io.microdrive.accounts.web;

import io.microdrive.accounts.domain.Account;
import io.microdrive.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


@Component
@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountService accountService;

    @GetMapping("/current")
    public Account getCurrent(@AuthenticationPrincipal Account account) {
        return account;
    }

    @GetMapping("/drivers/{id}")
    public Account getDriverById(@PathVariable String id) {
        return accountService.findDriver(id)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found"));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create() {
        throw new NotImplementedException();
    }

}

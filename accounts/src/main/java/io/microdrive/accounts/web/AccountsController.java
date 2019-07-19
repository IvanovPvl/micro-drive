package io.microdrive.accounts.web;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.microdrive.accounts.domain.Account;
import io.microdrive.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@Component
@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountService accountService;
    private final KeyPair keyPair;

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

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}

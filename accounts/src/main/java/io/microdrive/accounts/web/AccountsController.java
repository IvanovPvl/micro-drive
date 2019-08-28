package io.microdrive.accounts.web;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.microdrive.accounts.domain.Account;
import io.microdrive.accounts.errors.ResponseError;
import io.microdrive.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

@Component
@RequiredArgsConstructor
@RestController
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
    public ResponseEntity create(@RequestBody Account account) {
        val result = accountService.create(account);
        if (result.isSuccess()) {
            return status(CREATED).build();
        }
        val error = result.error();
        return badRequest().body(ResponseError.builder().message(error.getMessage()).build());
    }

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}

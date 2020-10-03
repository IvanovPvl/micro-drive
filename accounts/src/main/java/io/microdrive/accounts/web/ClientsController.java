package io.microdrive.accounts.web;

import io.microdrive.accounts.service.AccountService;
import io.microdrive.accounts.web.dto.CreateClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientsController {

    private final AccountService accountService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> create(@RequestBody CreateClientRequest request) {
        return accountService.create(request).then();
    }

//    @GetMapping("/.well-known/jwks.json")
//    public Map<String, Object> getKey() {
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAKey key = new RSAKey.Builder(publicKey).build();
//        return new JWKSet(key).toJSONObject();
//    }

}

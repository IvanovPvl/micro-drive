package io.microdrive.account.web;

import io.microdrive.account.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @GetMapping("/current")
    public Mono<User> getUser(@AuthenticationPrincipal User user) {
        return Mono.just(user);
    }
}

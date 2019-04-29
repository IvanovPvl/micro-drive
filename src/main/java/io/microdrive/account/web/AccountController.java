package io.microdrive.account.web;

import io.microdrive.account.domain.User;
import io.microdrive.account.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @GetMapping("/current")
    public Mono<User> getCurrent(@AuthenticationPrincipal User user) {
        return Mono.just(user);
    }

    @PostMapping
    public Mono<User> create(@RequestBody User user) {
        return Mono.just(this.userService.create(user));
    }
}

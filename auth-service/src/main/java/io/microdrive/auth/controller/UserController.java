package io.microdrive.auth.controller;

import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import javax.validation.Valid;

import io.microdrive.auth.domain.User;
import io.microdrive.auth.service.UserService;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/current")
    public Mono<User> getUser(@AuthenticationPrincipal User user) {
        return Mono.just(user);
    }

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('server')")
    public Mono<User> createUser(@Valid @RequestBody User user) {
        return Mono.just(this.userService.create(user));
    }
}

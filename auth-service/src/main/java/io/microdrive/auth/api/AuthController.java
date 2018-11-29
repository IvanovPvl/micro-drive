package io.microdrive.auth.api;

import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import io.microdrive.auth.domain.Role;
import io.microdrive.auth.domain.User;
import io.microdrive.auth.domain.RegisterRequest;
import io.microdrive.auth.domain.RegisterResponse;
import io.microdrive.auth.repository.UserRepository;
import io.microdrive.auth.repository.RoleRepository;

import java.util.Set;
import java.util.HashSet;

@RestController
@RequestMapping("/auth")
@EnableAuthorizationServer
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthController(PasswordEncoder passwordEncoder, UserRepository userRepo, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RegisterResponse> create(@RequestBody RegisterRequest request) {
        Set<Role> roles = new HashSet<>();

        Role role = roleRepository.findByName(request.getRole()).get(); // TODO: check role
        roles.add(role);

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        user = userRepo.save(user);
        return Mono.just(new RegisterResponse(user.getId()));
    }
}

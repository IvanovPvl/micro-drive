package io.microdrive.accounts.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final TokenParser tokenParser;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just((String)authentication.getPrincipal())
            .map(tokenParser::parse)
            .map(claims -> new UsernamePasswordAuthenticationToken(
                new Principal(claims.getSubject()),
                null,
                Collections.singletonList(new SimpleGrantedAuthority(claims.get("role", String.class)))
            ));
    }
}

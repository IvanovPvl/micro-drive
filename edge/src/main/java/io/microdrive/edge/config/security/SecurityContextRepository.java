package io.microdrive.edge.config.security;

import io.jsonwebtoken.Claims;
import io.microdrive.edge.config.security.types.Principal;
import io.microdrive.edge.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.just;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        var header = exchange.getRequest().getHeaders().get("Authorization");
        if (header == null) {
            return empty();
        }

        Claims claims;
        try {
            claims = tokenService.parse(header.get(0));
        } catch (Throwable ex) {
            return empty();
        }

        return just(new Principal(claims.getSubject(), "ROLE_" + claims.get("role", String.class)))
            .map(data -> new UsernamePasswordAuthenticationToken(data, data))
            .flatMap(authenticationManager::authenticate)
            .map(SecurityContextImpl::new);
    }
}

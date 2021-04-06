package io.microdrive.accounts.config.security;

import io.microdrive.accounts.config.security.types.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {
    private final AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        var accountHeader = exchange.getRequest().getHeaders().get("x-account-id");
        var roleHeader = exchange.getRequest().getHeaders().get("x-role");
        if (accountHeader == null || roleHeader == null) {
            return Mono.empty();
        }

        var userId = accountHeader.get(0);
        var role = roleHeader.get(0);
        return Mono.justOrEmpty(new Principal(userId, role))
            .map(data -> new UsernamePasswordAuthenticationToken(data, data))
            .flatMap(authenticationManager::authenticate)
            .map(SecurityContextImpl::new);
    }
}

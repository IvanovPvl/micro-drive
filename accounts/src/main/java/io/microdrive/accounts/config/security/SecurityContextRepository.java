package io.microdrive.accounts.config.security;

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
        var header = exchange.getRequest().getHeaders().get("Authorization");
        if (header == null) {
            return Mono.empty();
        }

        var jwt = header.get(0);
        return Mono.justOrEmpty(jwt)
            .map(token -> new UsernamePasswordAuthenticationToken(token, token))
            .flatMap(authenticationManager::authenticate)
            .map(SecurityContextImpl::new);
    }
}

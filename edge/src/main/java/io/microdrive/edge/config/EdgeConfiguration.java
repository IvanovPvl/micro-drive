package io.microdrive.edge.config;

import io.microdrive.edge.config.security.types.Principal;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EdgeConfiguration {
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public GlobalFilter passRoleAccountIdFilter() {
        return (exchange, chain) -> ReactiveSecurityContextHolder.getContext()
            .map(context -> (Principal) context.getAuthentication().getPrincipal())
            .map(principal -> {
                exchange.getRequest()
                    .mutate()
                    .header("x-role", principal.role())
                    .header("x-account-id", principal.id())
                    .build();
                return exchange;
            })
            .flatMap(chain::filter);
    }
}

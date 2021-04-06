package io.microdrive.edge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
    private final String[] WHITE_LIST = new String[]{
        "/actuator/**",
        "/api/v1/accounts",
        "/api/v1/accounts/check-password",
        "/api/v1/accounts/actuator/**"
    };

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        http.exceptionHandling()
                .and()
                .authorizeExchange()
                .pathMatchers(WHITE_LIST).permitAll()
                .anyExchange().authenticated()
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .logout().disable();

        return http.build();
    }
}

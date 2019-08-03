package io.microdrive.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    private final String[] WHITE_LIST = new String[]{
            "/actuator/**",
            "/accounts/oauth/token",
            "/accounts/create"
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
                .logout().disable()
                .oauth2ResourceServer().jwt();

        return http.build();
    }

}

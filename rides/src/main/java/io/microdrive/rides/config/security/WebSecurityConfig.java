package io.microdrive.rides.config.security;

import io.microdrive.coresecurity.AuthenticationManager;
import io.microdrive.coresecurity.SecurityContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
public class WebSecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    private static final String[] WHITE_LIST = {
        "/actuator/**",
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .httpBasic().disable()
            .formLogin().disable()
            .logout().disable()
            .cors().disable()
            .csrf().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
            .pathMatchers(WHITE_LIST).permitAll()
            .anyExchange().authenticated().and().build();
    }
}

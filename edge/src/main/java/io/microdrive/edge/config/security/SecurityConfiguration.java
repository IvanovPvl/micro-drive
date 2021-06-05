package io.microdrive.edge.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@EnableConfigurationProperties(SecurityConfigurationProperties.class)
public class SecurityConfiguration {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    private final String[] WHITE_LIST = new String[]{
        "/actuator/**",
        "/api/accounts",
        "/api/accounts/token"
    };

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        http
            .httpBasic().disable()
            .formLogin().disable()
            .logout().disable()
            .cors().disable()
            .csrf().disable()
            .authorizeExchange().pathMatchers(WHITE_LIST).permitAll();

        http
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository);

        // TODO: get url path from configuration
        http.authorizeExchange()
            .pathMatchers("/api/rides/get-info").hasRole("CLIENT")
            .pathMatchers(HttpMethod.POST, "/api/rides").hasRole("CLIENT")
            .pathMatchers(HttpMethod.PUT, "/api/rides").hasRole("DRIVER")
            .pathMatchers(HttpMethod.POST, "/api/drivers-control/add-to-free").hasRole("DRIVER")
            .pathMatchers(HttpMethod.POST, "/api/drivers-control/find-free").hasRole("CLIENT");

        http.authorizeExchange().anyExchange().authenticated();
        return http.build();
    }
}

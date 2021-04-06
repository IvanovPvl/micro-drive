package io.microdrive.edge.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableConfigurationProperties(SecurityConfigurationProperties.class)
public class SecurityConfiguration {
    private final String[] WHITE_LIST = new String[]{
        "/actuator/**",
        "/api/v1/accounts",
        "/api/v1/accounts/token",
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

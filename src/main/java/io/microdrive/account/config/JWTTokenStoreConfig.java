package io.microdrive.account.config;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@RequiredArgsConstructor
public class JWTTokenStoreConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        val tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        return tokenServices;
    }

    @Bean
    public UserAuthenticationConverter userAuthenticationConverter() {
        DefaultUserAuthenticationConverter converter = new DefaultUserAuthenticationConverter();
        converter.setUserDetailsService(this.userDetailsService);
        return converter;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        val converter = new JwtAccessTokenConverter();

        DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
        tokenConverter.setUserTokenConverter(userAuthenticationConverter());
        converter.setAccessTokenConverter(tokenConverter);

        converter.setSigningKey("key"); // TODO: get key via config
        return converter;
    }
}

package io.microdrive.accounts.config.security;

import io.micrometer.core.instrument.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.io.IOException;
import java.security.KeyPair;

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
    public KeyPair keyPair() {
        val factory = new KeyStoreKeyFactory(new ClassPathResource("secret.jks"), "secret".toCharArray());
        return factory.getKeyPair("secret");
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        val converter = new JwtAccessTokenConverter();

        DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
        tokenConverter.setUserTokenConverter(userAuthenticationConverter());
        converter.setAccessTokenConverter(tokenConverter);

        converter.setKeyPair(keyPair());
        converter.setVerifierKey(getKey());
        return converter;
    }

    private static String getKey() {
        val resource = new ClassPathResource("key");
        String key;
        try {
            key = IOUtils.toString(resource.getInputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return key;
    }

}

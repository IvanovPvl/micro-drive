package io.microdrive.accounts.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;

@Configuration
@EnableWebSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .httpBasic().disable()
            .formLogin().disable()
            .logout().disable()
            .cors().disable()
            .csrf().disable()
            .authorizeExchange()
            .anyExchange().permitAll().and().build();
    }

    @Bean
    public KeyPair keyPair() {
        try (var inputStream = new ClassPathResource("secret.jks").getInputStream()) {
            var keyStore = KeyStore.getInstance("jks");
            keyStore.load(inputStream, "secret".toCharArray());
            RSAPrivateCrtKey key = (RSAPrivateCrtKey) keyStore.getKey("secret", "secret".toCharArray());
            RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
            return new KeyPair(publicKey, key);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load keys from store: ", e);
        }
    }
}

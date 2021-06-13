package io.microdrive.edge.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.microdrive.core.types.accounts.CheckPasswordResponse;
import io.microdrive.edge.config.security.SecurityConfigurationProperties;
import io.microdrive.edge.types.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.KeyStore;
import java.time.temporal.ChronoUnit;

import static java.time.Instant.now;
import static java.util.Date.from;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtBuilder jwtBuilder = Jwts.builder();
    private final SecurityConfigurationProperties properties;

    @Value("classpath:secret.jks")
    private Resource secret;
    private final JwtParser jwtParser = Jwts.parserBuilder().build();

    @PostConstruct
    private void initialize() {
        try {
            var keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(secret.getInputStream(), properties.getKeyStorePassword().toCharArray());
            var key = keyStore.getKey(properties.getAlias(), properties.getKeyPassword().toCharArray());
            jwtBuilder.signWith(key, SignatureAlgorithm.RS256);
            jwtParser.setSigningKey(key);
        } catch (Throwable ex) {
            log.error("Exception during initialize: ", ex);
        }
    }

    public Token create(CheckPasswordResponse response) {
        var issuedAt = now();
        var expiredAt = issuedAt.plus(1, ChronoUnit.DAYS);
        var accessToken = jwtBuilder
            .setHeaderParam("typ", "JWT")
            .setSubject(response.accountId())
            .claim("role", response.role())
            .setIssuedAt(from(issuedAt))
            .setExpiration(from(expiredAt))
            .compact();

        return new Token(accessToken, issuedAt.toEpochMilli(), expiredAt.toEpochMilli());
    }

    public Claims parse(String token) {
        return jwtParser
            .parseClaimsJwt(token)
            .getBody();
    }
}

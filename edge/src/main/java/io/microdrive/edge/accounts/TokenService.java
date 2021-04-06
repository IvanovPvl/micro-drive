package io.microdrive.edge.accounts;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.microdrive.core.types.accounts.CheckPasswordResponse;
import io.microdrive.edge.config.SecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.KeyStore;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtBuilder jwtBuilder = Jwts.builder();
    private final SecurityConfigurationProperties properties;

    @PostConstruct
    private void initialize() {
        try {
            var keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(secret.getInputStream(), properties.getKeyStorePassword().toCharArray());
            var key = keyStore.getKey(properties.getAlias(), properties.getKeyPassword().toCharArray());
            jwtBuilder.signWith(key, SignatureAlgorithm.RS256);
        } catch (Exception ex) {
            // TODO: log ex
        }
    }

    @Value("classpath:secret.jks")
    private Resource secret;

    public Token create(CheckPasswordResponse response) {
        System.out.println(response);

        var issuedAt = Instant.now();
        var expiredAt = issuedAt.plus(1, ChronoUnit.DAYS);
        var accessToken = jwtBuilder
            .setHeaderParam("typ", "JWT")
            .setSubject(response.getAccountId())
            .claim("role", response.getRole())
            .setIssuedAt(Date.from(issuedAt))
            .setExpiration(Date.from(expiredAt))
            .compact();

        return new Token(accessToken, issuedAt.toEpochMilli(), expiredAt.toEpochMilli());
    }
}

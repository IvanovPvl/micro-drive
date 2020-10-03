package io.microdrive.accounts.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.microdrive.accounts.errors.AccountNotFound;
import io.microdrive.accounts.repository.AccountRepository;
import io.microdrive.accounts.web.dto.AuthRequest;
import io.microdrive.accounts.web.dto.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.KeyPair;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final KeyPair keyPair;
    private final JwtBuilder builder = Jwts.builder();

    public Mono<Token> create(AuthRequest request) {
        return accountRepository.findByPhoneNumber(request.getPhoneNumber())
            .flatMap(account -> {
                if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
                    return Mono.error(new AccountNotFound(request.getPhoneNumber()));
                }

                var issuedAt = Instant.now();
                var expiredAt = issuedAt.plus(1, ChronoUnit.DAYS);
                var token = builder.setSubject(account.getId())
                    .setHeaderParam("typ", "JWT")
                    .claim("isClient", true)
                    .claim("isDriver", false)
                    .setIssuedAt(Date.from(issuedAt))
                    .setExpiration(Date.from(expiredAt))
                    .signWith(keyPair.getPrivate())
                    .compact();

                return Mono.just(new Token(token, issuedAt.getEpochSecond(), expiredAt.getEpochSecond()));
            }).switchIfEmpty(Mono.error(new AccountNotFound(request.getPhoneNumber())));
    }
}

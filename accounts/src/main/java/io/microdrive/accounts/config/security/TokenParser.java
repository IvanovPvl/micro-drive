package io.microdrive.accounts.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class TokenParser {
    private final JwtParser parser = Jwts.parserBuilder().build();

    public Claims parse(String token) {
        return parser.parseClaimsJws(token).getBody();
    }
}

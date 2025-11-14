package com.example.backendapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * PUBLIC_INTERFACE
 * Service to create and validate JWT access tokens.
 */
@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expiryMillis;

    public JwtService(
            @Value("${app.security.jwt-secret:change_me_please_for_dev_only_change_me_please_for_dev_only}") String secret,
            @Value("${app.security.jwt-exp-min:60}") long expMinutes
    ) {
        byte[] key = Decoders.BASE64.decode(toBase64IfRaw(secret));
        this.secretKey = Keys.hmacShaKeyFor(key);
        this.expiryMillis = expMinutes * 60_000;
    }

    private String toBase64IfRaw(String maybeRaw) {
        // If already base64, keep; else base64-encode UTF-8 bytes (simple heuristic)
        try {
            Decoders.BASE64.decode(maybeRaw);
            return maybeRaw;
        } catch (Exception ex) {
            return java.util.Base64.getEncoder().encodeToString(maybeRaw.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
    }

    // PUBLIC_INTERFACE
    public String generateToken(String subject, Map<String, Object> claims) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(new Date(now.toEpochMilli() + expiryMillis))
                .signWith(secretKey)
                .compact();
    }

    // PUBLIC_INTERFACE
    public boolean isTokenValid(String token, String expectedSubject) {
        try {
            Claims claims = parse(token);
            if (claims.getExpiration() == null || claims.getExpiration().before(new Date())) {
                return false;
            }
            return expectedSubject.equals(claims.getSubject());
        } catch (Exception ex) {
            return false;
        }
    }

    // PUBLIC_INTERFACE
    public String extractSubject(String token) {
        return parse(token).getSubject();
    }

    private Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

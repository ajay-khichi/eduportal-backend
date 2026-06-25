package com.ignishers.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {


    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpirationMs;

    // ──────────────────────────────────────────────────────
    //  Token Generation
    // ──────────────────────────────────────────────────────

    /** Generates an access token with default claims (subject = email). */
    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails.getUsername(), jwtExpirationMs);
    }

    /** Generates an access token with additional custom claims. */
    public String generateAccessToken(Map<String, Object> extraClaims,UserDetails userDetails) {
        return buildToken(extraClaims, userDetails.getUsername(), jwtExpirationMs);
    }

    @SuppressWarnings("java:S2143")
    private String buildToken(Map<String, Object> extraClaims, String subject, long expiryMs) {
        // 1. Capture the exact current timestamp as a high-precision Instant
        Instant now = Instant.now();
        // 2. Calculate the exact future expiration instant by adding milliseconds
        Instant expirationTime = now.plusMillis(expiryMs);
        return Jwts.builder()
                .claims(extraClaims)
                .subject(subject)
                .issuedAt(java.util.Date.from(now))
                .expiration(java.util.Date.from(expirationTime))
                .signWith(getSigningKey())
                .compact();
    }

    // ──────────────────────────────────────────────────────
    //  Token Validation
    // ──────────────────────────────────────────────────────
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String email = extractEmail(token);
            return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).isBefore(Instant.now());
    }



    // ──────────────────────────────────────────────────────
    //  Claims Extraction
    // ──────────────────────────────────────────────────────
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Instant extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration).toInstant();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);

    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ──────────────────────────────────────────────────────
    //  Key
    // ──────────────────────────────────────────────────────
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}

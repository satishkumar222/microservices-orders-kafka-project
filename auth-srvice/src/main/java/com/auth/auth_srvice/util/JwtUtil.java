package com.auth.auth_srvice.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    // Generate Secret Key
    private SecretKey getSignKey() {

        return Keys.hmacShaKeyFor(secretKey.getBytes());

    }

    // Generate JWT Token
    public String generateToken(Long userId,
                                String email,
                                String role) {

        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey())
                .compact();

    }

    // Read All Claims
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    // Read Email
    public String extractEmail(String token) {

        return extractAllClaims(token).getSubject();

    }

    // Read User Id
    public Long extractUserId(String token) {

        return extractAllClaims(token)
                .get("userId", Long.class);

    }

    // Read Role
    public String extractRole(String token) {

        return extractAllClaims(token)
                .get("role", String.class);

    }

    // Read Expiration
    public Date extractExpiration(String token) {

        return extractAllClaims(token)
                .getExpiration();

    }

    // Check Token Expired
    private boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());

    }

    // Validate Token
    public boolean validateToken(String token, String email) {

        return extractEmail(token).equals(email)
                && !isTokenExpired(token);

    }

}
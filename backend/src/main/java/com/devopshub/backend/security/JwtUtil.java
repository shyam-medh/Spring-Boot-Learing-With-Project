package com.devopshub.backend.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // 1. The Manager's Stamp (Changes every time server restarts for now!)
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // 2. 24 hours in milliseconds
    private final long expirationTime = 86400000; 

    // ACTION 1: Print a new Wristband
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // The person wearing the wristband
                .setIssuedAt(new Date()) // When it was printed
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // When it expires
                .signWith(key) // The Manager's unforgeable signature
                .compact();
    }

    // ACTION 2: Read the name on the Wristband
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // We must use the exact same signature to read it!
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // ACTION 3: Verify the Wristband is legit
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}

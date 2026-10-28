package com.devopshub.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Security Utility: Handles cryptographic signing, parsing, and claims verification
 * for JSON Web Tokens (JWT) using HMAC-SHA256.
 */
@Component
public class JwtUtil {

    private final Key signingKey;
    private final long expirationMs;

    /**
     * Caption: Constructor & Key Initialization
     * Initializes the cryptographic HMAC signing key from application properties
     * (or default 256-bit secret) and sets token time-to-live.
     */
    public JwtUtil(
            @Value("${jwt.secret:devopshub-super-secret-jwt-signing-key-must-be-at-least-256-bits}") String secret,
            @Value("${jwt.expiration-ms:86400000}") long expirationMs
    ) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    /**
     * Caption: Generate JWT Token
     * Constructs a compact signed JWT token containing subject (username), custom claims (role),
     * issue timestamp, and expiration timestamp signed with HMAC-SHA256.
     *
     * @param username The authenticated user's login handle
     * @param role The user's assigned permission role (e.g., "USER", "ADMIN")
     * @return Compact serialized JWT string
     */
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(signingKey)
                .compact();
    }

    /**
     * Caption: Extract Username Claim
     * Decodes the JWT token and extracts the subject header (username).
     *
     * @param token Compact signed JWT string
     * @return Extracted username string
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Caption: Extract Role Claim
     * Decodes the JWT token and extracts the custom "role" claim.
     *
     * @param token Compact signed JWT string
     * @return Extracted role string
     */
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    /**
     * Caption: Validate Token Integrity & Expiration
     * Confirms that the token's subject matches the expected username and that the token is not expired.
     *
     * @param token Compact signed JWT string
     * @param username The expected username
     * @return true if token is genuine, valid, and unexpired; false otherwise
     */
    public boolean validateToken(String token, String username) {
        final String extractedUser = extractUsername(token);
        return (extractedUser.equals(username) && !isTokenExpired(token));
    }

    /**
     * Caption: Check Token Expiration
     * Inspects the 'exp' claim to verify if the token timestamp is in the past.
     *
     * @param token Compact signed JWT string
     * @return true if expired, false if currently valid
     */
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /**
     * Caption: Parse and Verify All Claims
     * Cryptographically verifies the signature using the HMAC signing key and decodes the claims body.
     *
     * @param token Compact signed JWT string
     * @return Claims payload
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

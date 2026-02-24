package me.andrey20005.web4.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;

public class JwtUtil {

    public static String generateToken(Long userId, String username, String secretKey) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 3600000);

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("userId", userId)
                .claim("username", username)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

    public static Claims validateToken(String token, String secretKey) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

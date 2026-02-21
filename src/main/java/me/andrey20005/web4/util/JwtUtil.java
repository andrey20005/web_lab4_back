package me.andrey20005.web4.util;

import java.util.Date;
import io.jsonwebtoken.*;

public class JwtUtil {

    public static String generateToken(Long userId, String username, String secretKey) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 3600000); // 1 час

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("userId", userId)
                .claim("username", username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static Claims validateToken(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}

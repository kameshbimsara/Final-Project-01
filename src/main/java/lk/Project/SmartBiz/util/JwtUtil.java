package lk.Project.SmartBiz.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // ✅ Use a strong 256-bit key
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "ThisIsA256BitStrongSecretKeyForJWTs1234567890".getBytes() // >= 32 chars
    );

    // Token valid for 24 hours
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // ✅ Generate JWT Token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Validate JWT Token
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            throw new RuntimeException("Invalid or expired JWT token");
        }
    }

    // ✅ Extract Username
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

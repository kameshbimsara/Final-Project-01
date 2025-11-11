package lk.Project.SmartBiz.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    // ✅ Use a strong 256-bit (32+ characters) secret key
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "ThisIsA256BitStrongSecretKeyForJWTs1234567890".getBytes()
    );

    // ✅ Token validity: 24 hours
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    /**
     * ✅ Generate JWT Token with username and role claims
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .addClaims(Map.of("role", role))  // include role (e.g., ADMIN / OWNER)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * ✅ Validate the given JWT token
     * Throws RuntimeException if invalid or expired
     */
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

    /**
     * ✅ Extract the username (subject) from the token
     */
    public String extractUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    /**
     * ✅ Extract the role claim from the token
     */
    public String extractRole(String token) {
        return (String) getAllClaims(token).get("role");
    }

    /**
     * 🔒 Helper method to get all claims (payload data)
     */
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

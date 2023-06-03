package us.jameschan.supervisor.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Json Web Token based on JJWT.
 */
@Component
public class Token {
    private final SecretKey secretKey;

    private final Integer tokenValidityPeriod;

    @Autowired
    private Token(String tokenSecret, Integer tokenValidityPeriod) {
        secretKey = Keys.hmacShaKeyFor(tokenSecret.getBytes());

        this.tokenValidityPeriod = tokenValidityPeriod;
    }

    /**
     * Generates and returns a token
     * @param userId the id of the user.
     * @return a token.
     */
    public String generate(Long userId) {
        final Date date = new Date();
        final Date expiryDate = new Date(date.getTime() + tokenValidityPeriod);

        final Claims claims = Jwts.claims();
        claims.put("id", userId);

        return Jwts.builder()
            .setIssuedAt(date)
            .setExpiration(expiryDate)
            .signWith(secretKey)
            .setClaims(claims)
            .compact();
    }

    /**
     * Retrieves user's id from the given token string.
     * @param token token string.
     * @return user'id; null if the validation fails.
     */
    public Long getUserId(String token) {
        try {
            final JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();

            final Claims claims = parser.parseClaimsJws(token).getBody();
            return (long) ((double) claims.get("id"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

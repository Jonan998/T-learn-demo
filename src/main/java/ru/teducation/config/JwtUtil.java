package ru.teducation.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private final AppProperties properties;

  public JwtUtil(AppProperties properties) {
    this.properties = properties;
  }

  protected long expirationMillis() {
    return properties.getJwt().getExpirationMillis();
  }

  protected SecretKey getKey() {
    return Keys.hmacShaKeyFor(properties.getJwt().getSecret().getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String username, int userId) {

    Date now = new Date();
    Date exp = new Date(now.getTime() + expirationMillis());

    return Jwts.builder()
        .subject(String.valueOf(userId))
        .claim("username", username)
        .issuedAt(now)
        .expiration(exp)
        .signWith(getKey(), Jwts.SIG.HS256)
        .compact();
  }

  public Claims getClaims(String token) {
    return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
  }

  public boolean isExpired(String token) {
    try {
      return getClaims(token).getExpiration().before(new Date());
    } catch (Exception e) {
      return true;
    }
  }
}

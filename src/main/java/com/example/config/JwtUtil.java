package com.example.config;

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
    return properties.getExpirationMillis();
  }

  protected SecretKey getKey() {
    return Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String username, int userId) {

    Date now = new Date();
    Date exp = new Date(now.getTime() + expirationMillis());

    return Jwts.builder()
        .subject(username)
        .claim("userId", userId)
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

  public Integer extractUserId(String token) {
    try {
      return getClaims(token).get("userId", Integer.class);
    } catch (Exception e) {
      return null;
    }
  }
}

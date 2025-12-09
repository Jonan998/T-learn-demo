package com.example.service;

import com.example.config.JwtUtil;
import com.example.exception.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
  private static final String BEARER_PREFIX = "Bearer ";
  private final JwtUtil jwtUtil;

  public AuthService(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  public Integer getUserId(HttpServletRequest request) {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header == null || !header.startsWith(BEARER_PREFIX)) {
      throw new AuthenticationException("Неверный токен");
    }

    return jwtUtil.extractUserId(header.substring(BEARER_PREFIX.length()));
  }
}

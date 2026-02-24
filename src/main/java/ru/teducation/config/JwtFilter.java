package ru.teducation.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.teducation.Security.UserPrincipal;
import ru.teducation.service.UserDetailsServiceImpl;

// Импорт JwtUtil может измениться, если ты его тоже переместил

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  // Best Practice: Выносим "магические числа" в статические константы
  private static final String BEARER_PREFIX = "Bearer ";

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    // 1. Используем константу BEARER_PREFIX
    if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    // 2. Используем длину константы вместо "7"
    String token = authHeader.substring(BEARER_PREFIX.length());

    try {
      Claims claims = jwtUtil.getClaims(token);
      int userId = Integer.parseInt(claims.getSubject());

      if (SecurityContextHolder.getContext().getAuthentication() == null) {

        UserPrincipal principal = (UserPrincipal) userDetailsService.loadUserById(userId);

        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    chain.doFilter(request, response);
  }
}

package ru.teducation.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ru.teducation.Security.UserPrincipal;
import ru.teducation.exception.TooManyRequestException;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

  private final RateLimiterRegistry rateLimiterRegistry;
  private final HandlerExceptionResolver handlerExceptionResolver;
  private final AppProperties appProperties;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null
        || !auth.isAuthenticated()
        || !(auth.getPrincipal() instanceof UserPrincipal principal)) {
      chain.doFilter(request, response);
      return;
    }

    String limiterName = "user-" + principal.getId();
    RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(limiterName, "default");

    if (!rateLimiter.acquirePermission()) {

      int retryAfterSeconds = appProperties.getRateLimit().getRetryAfterSeconds();
      String message = appProperties.getRateLimit().getMessage();

      handlerExceptionResolver.resolveException(
          request, response, null, new TooManyRequestException(message, retryAfterSeconds));
      return;
    }

    chain.doFilter(request, response);
  }
}

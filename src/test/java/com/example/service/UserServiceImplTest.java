package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.dto.UserDto;
import com.example.dto.UserLimitsView;
import com.example.repository.UserRepository;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserServiceImplTest {

  @Mock private UserRepository userRepository;

  private UserService userService;

  @Mock private RateLimitService rateLimitService;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);

    when(rateLimitService.isRateLimitExceeded(anyInt(), anyInt(), any(Duration.class)))
        .thenReturn(false);

    SecurityContext context = SecurityContextHolder.createEmptyContext();

    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken("testUser", null, List.of());

    context.setAuthentication(auth);
    SecurityContextHolder.setContext(context);

    userService = new UserServiceImpl(userRepository, null, null, rateLimitService);
  }

  @AfterEach
  void clearSecurityContext() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void testGetUserLimits() {

    UserLimitsView limitsViewMock = mock(UserLimitsView.class);

    when(limitsViewMock.getLimitNew()).thenReturn(10);
    when(limitsViewMock.getLimitRepeat()).thenReturn(10);

    when(userRepository.findUserLimits(4)).thenReturn(limitsViewMock);

    UserDto result = userService.getUserLimits(4);

    assertEquals(10, result.getLimitNew());
    assertEquals(10, result.getLimitRepeat());

    verify(userRepository, times(1)).findUserLimits(4);
  }
}

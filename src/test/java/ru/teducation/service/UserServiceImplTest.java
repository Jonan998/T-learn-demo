package ru.teducation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.teducation.dto.UserDto;
import ru.teducation.dto.UserLimitsView;
import ru.teducation.model.User;
import ru.teducation.repository.UserRepository;

public class UserServiceImplTest {

  @Mock private UserRepository userRepository;
  @Mock private PasswordEncoder passwordEncoder;

  private UserService userService;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);

    SecurityContext context = SecurityContextHolder.createEmptyContext();

    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken("testUser", null, List.of());

    context.setAuthentication(auth);
    SecurityContextHolder.setContext(context);

    userService = new UserServiceImpl(userRepository, passwordEncoder, null);
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

  @Test
  void testUpdateUserSettings() {
    int userId = 1;

    User user = new User();
    user.setId(userId);
    user.setPassword("encodedPassword");

    UserDto updUser = new UserDto("Васек", 30, 30, "password", "newPassword");

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

    userService.updateUserSettings(userId, updUser);

    verify(userRepository).save(user);
  }
}

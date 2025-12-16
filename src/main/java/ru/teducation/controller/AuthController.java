package ru.teducation.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.teducation.config.JwtUtil;
import ru.teducation.dto.AuthResponse;
import ru.teducation.dto.LoginRequest;
import ru.teducation.exception.AuthenticationException;
import ru.teducation.model.User;
import ru.teducation.repository.UserRepository;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  private static final String LOGIN_PATH = "/login";

  @PostMapping(LOGIN_PATH)
  public AuthResponse login(@RequestBody LoginRequest request) {
    Optional<User> userOpt = userRepository.findByName(request.getUsername());

    if (userOpt.isEmpty()) {
      throw new AuthenticationException("User not found");
    }

    User user = userOpt.get();

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new AuthenticationException("Invalid password");
    }

    String token = jwtUtil.generateToken(user.getName(), user.getId());

    return new AuthResponse(token, user.getName());
  }
}

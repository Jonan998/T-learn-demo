package ru.teducation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.teducation.dto.AuthResponse;
import ru.teducation.dto.LoginRequest;
import ru.teducation.service.AuthServiceImpl;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthServiceImpl authService;

  private static final String LOGIN_PATH = "/login";

  @PostMapping(LOGIN_PATH)
  public AuthResponse login(@RequestBody LoginRequest request) {
    return authService.loginOrRegister(request);
  }
}

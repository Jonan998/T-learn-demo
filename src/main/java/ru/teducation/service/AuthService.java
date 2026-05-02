package ru.teducation.service;

import ru.teducation.dto.AuthResponse;
import ru.teducation.dto.LoginRequest;

public interface AuthService {
  AuthResponse loginOrRegister(LoginRequest request);
}

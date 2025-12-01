package com.example.controller;

import com.example.dto.AuthResponse;
import com.example.dto.LoginRequest;
import com.example.exception.AuthenticationException;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.Security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
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
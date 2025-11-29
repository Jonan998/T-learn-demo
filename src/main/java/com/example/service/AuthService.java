package com.example.service;

import com.example.Security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    private final JwtUtil jwtUtil;

    public AuthService(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    public Integer getUserId(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")){
            throw new RuntimeException("Неверный токен");
        }

        return jwtUtil.extractUserId(header.substring(7));
    }
}
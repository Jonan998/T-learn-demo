package com.example.service;

import com.example.dto.UserDto;
import com.example.model.User;

import java.time.LocalDate;

public interface UserService {
    UserDto getUser(int userId);
    void createUser(String name, String password, LocalDate created_at_new, LocalDate created_at_repeat, int limit_new, int limit_repeat);
}

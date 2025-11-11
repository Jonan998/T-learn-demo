package com.example.service;

import com.example.dto.UserDto;
import com.example.model.User;

public interface UserService {
    UserDto getUser(int userId);
    void createUser(String name, String password);
}

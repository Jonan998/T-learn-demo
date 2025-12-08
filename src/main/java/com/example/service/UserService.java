package com.example.service;

import com.example.dto.UserDto;
import com.example.model.User;

import java.time.LocalDate;

public interface UserService {
    UserDto getUser(int userId);

    void createUser(String name, String password, LocalDate createdAtNew, LocalDate createdAtRepeat, int limitNew, int limitRepeat);

    UserDto getUserLimits(int userId);

    void updateUserSettings(int userId, UserDto dto);
}
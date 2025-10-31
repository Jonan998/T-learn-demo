package com.example.service;

import com.example.model.User;

public interface UserService {
    User getUser(int userId);
    void createUser(String name, String password);
}

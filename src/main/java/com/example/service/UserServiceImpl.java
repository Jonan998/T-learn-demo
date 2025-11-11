package com.example.service;

import com.example.model.User;
import com.example.dto.UserDto;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository repository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) { 
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getUser(int userId) { 
        User user = repository.findById(userId).orElse(null);
        return user != null ? userMapper.toDto(user) : null;
    }

    @Override
    public void createUser(String name, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        repository.save(new User(name, encodedPassword));
    }
    
    public UserDto createUser(UserDto userDto, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(userDto.getName(), encodedPassword);
        User savedUser = repository.save(user);
        return userMapper.toDto(savedUser);
    }
}
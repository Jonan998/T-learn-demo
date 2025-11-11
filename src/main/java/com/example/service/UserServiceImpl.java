package com.example.service;

import com.example.model.User;
import com.example.dto.UserDto;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
    public void createUser(String name, String password, LocalDate created_at_new, LocalDate created_at_repeat, int limit_new, int limit_repeat) {
        String encodedPassword = passwordEncoder.encode(password);
        repository.save(new User(name, encodedPassword,created_at_new,created_at_repeat,limit_new,limit_repeat));
    }
    
    public UserDto createUser(UserDto userDto, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(userDto.getName(), encodedPassword, userDto.getCreated_at_new(),userDto.getCreated_at_repeat(),userDto.getLimit_new(),userDto.getLimit_repeat());
        User savedUser = repository.save(user);
        return userMapper.toDto(savedUser);
    }
}
package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User getUser(int userid) {
        return repository.findById(userid).orElse(null);
    }
    @Override
    public void createUser(String name, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        repository.save(new User(name, encodedPassword));
    }
}

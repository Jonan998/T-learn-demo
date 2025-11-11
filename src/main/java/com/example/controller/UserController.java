package com.example.controller;

import com.example.dto.UserDto;
import com.example.model.User;
import com.example.service.UserService;
import com.example.service.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping
    public void createUser(@RequestParam String name,
                           @RequestParam String password){
        service.createUser(name, password);
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public UserDto getUser(@PathVariable int id){
        return service.getUser(id);
    }
}

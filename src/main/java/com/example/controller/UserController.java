package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.time.LocalDate;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final String ID_PATH = "/{id}";

    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping
    public void createUser(@RequestParam String name,
                           @RequestParam String password,
                           @RequestParam(name = "created_at_new") LocalDate createdAtNew,
                           @RequestParam(name = "created_at_repeat") LocalDate createdAtRepeat,
                           @RequestParam(name = "limit_new") int limitNew,
                           @RequestParam(name = "limit_repeat") int limitRepeat){

        service.createUser(name, password, createdAtNew, createdAtRepeat, limitNew, limitRepeat);
    }

    @GetMapping(value = ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUser(@PathVariable int id){
        return service.getUser(id);
    }
}
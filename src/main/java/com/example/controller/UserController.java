package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping
    public void createUser(@RequestParam String name,
                           @RequestParam String password,
                           @RequestParam LocalDate created_at_new,
                           @RequestParam LocalDate created_at_repeat,
                           @RequestParam int limit_new,
                           @RequestParam int limit_repeat){
        service.createUser(name, password,created_at_new,created_at_repeat,limit_new,limit_repeat);
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public UserDto getUser(@PathVariable int id){
        return service.getUser(id);
    }

    @GetMapping(value = "/settings", produces = "application/json; charset=UTF-8")
    public UserDto getUserLimits(@RequestParam("user_id") int userId){
        return service.getUserLimits(userId);
    }

    @PatchMapping(value = "/settings", produces = "application/json; charset=UTF-8")
    public void updateUserSettings(
            @RequestParam("user_id") int userId,
            @RequestBody UserDto dto
    ) {
        service.updateUserSettings(userId, dto);
    }

}

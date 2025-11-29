package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.AuthService;
import com.example.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;
    private final AuthService authService;

    public UserController(UserService service,AuthService authService){
        this.service = service;
        this.authService = authService;
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
    public UserDto getUserLimits(HttpServletRequest request){
        return service.getUserLimits(authService.getUserId(request));
    }

    @PatchMapping(value = "/settings", produces = "application/json; charset=UTF-8")
    public void updateUserSettings(
            HttpServletRequest request,
            @RequestBody UserDto dto
    ) {
        service.updateUserSettings(authService.getUserId(request), dto);
    }

}

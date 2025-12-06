package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.AuthService;
import com.example.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.time.LocalDate;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final String ID_PATH = "/{id}";
    private static final String SETTING_PATH = "/settings";

    private final UserService service;
    private final AuthService authService;

    public UserController(UserService service, AuthService authService){
        this.service = service;
        this.authService = authService;
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

    @GetMapping(value = SETTING_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUserLimits(HttpServletRequest request){
        return service.getUserLimits(authService.getUserId(request));
    }

    @PatchMapping(value = SETTING_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateUserSettings(
            HttpServletRequest request,
            @RequestBody UserDto dto
    ) {
        service.updateUserSettings(authService.getUserId(request), dto);
    }
}

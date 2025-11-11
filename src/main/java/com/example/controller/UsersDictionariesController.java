package com.example.controller;

import com.example.dto.UsersDictionariesDto;
import com.example.model.UsersDictionaries;
import com.example.service.UsersDictionariesService;
import com.example.service.UsersDictionariesServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users_dictionaries")
public class UsersDictionariesController {
    private final UsersDictionariesService service;

    public UsersDictionariesController(UsersDictionariesService service){
        this.service = service;
    }

    @PostMapping
    public void createUsers_dictionaries(@RequestParam int user_id,
                                         @RequestParam int dictionary_id,
                                         @RequestParam String is_active,
                                         @RequestParam int progress){
        service.createUsersDictionaries(user_id, dictionary_id, is_active, progress);
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public UsersDictionariesDto getUsers_dictionaries(@PathVariable int id){
        return service.getUsersDictionaries(id);
    }

}

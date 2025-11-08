package com.example.controller;

import com.example.model.UsersDictionaries;
import com.example.service.UsersDictionariesServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users_dictionaries")
public class Users_dictionariesController {
    private final UsersDictionariesServiceImpl service;

    public Users_dictionariesController(UsersDictionariesServiceImpl service){
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
    public UsersDictionaries getUsers_dictionaries(@PathVariable int id){
        return service.getUsersDictionaries(id);
    }

}

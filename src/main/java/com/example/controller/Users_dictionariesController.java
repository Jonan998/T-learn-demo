package com.example.controller;


import com.example.model.Users_dictionaries;
import com.example.service.Users_dictionariesServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users_dictionaries")
public class Users_dictionariesController {
    private final Users_dictionariesServiceImpl service;

    public Users_dictionariesController(Users_dictionariesServiceImpl service){
        this.service = service;
    }

    @PostMapping
    public void createUsers_dictionaries(@RequestParam int user_id,
                                                       @RequestParam int dictionary_id,
                                                       @RequestParam String is_active,
                                                       @RequestParam int progress){
        service.createUsers_dictionaries(user_id, dictionary_id, is_active, progress);
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public Users_dictionaries getUsers_dictionaries(@PathVariable int id){
        return service.getUsers_dictionaries(id);
    }

}

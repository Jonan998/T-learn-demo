package com.example.controller;

import com.example.dto.DictionaryDto;
import com.example.model.Dictionary;
import com.example.service.DictionaryService;
import com.example.service.DictionaryServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    private final DictionaryService service;

    public DictionaryController(DictionaryService service){
        this.service = service;
    }

    @PostMapping
    public void createDictionary(@RequestParam String name,
                                 @RequestParam String description,
                                 @RequestParam String language){
        service.createDictionary(name, description, language);
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public DictionaryDto getDictionary(@PathVariable int id){
        return service.getDictionary(id);
    }
}


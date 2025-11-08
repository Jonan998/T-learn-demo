package com.example.controller;

import com.example.model.DictionaryWords;
import com.example.service.DictionaryWordsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dictionary_words")
public class DictionaryWordsController {
    private final DictionaryWordsService service;

    public DictionaryWordsController(DictionaryWordsService service) {
        this.service = service;
    }

    @PostMapping
    public void createDictionary_words(@RequestParam int word_id,
                                       @RequestParam int dictionary_id) {
        service.createDictionaryWords(word_id, dictionary_id);
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public DictionaryWords getDictionary_words(@PathVariable int id) {
            return service.getDictionaryWords(id);
    }
}
package com.example.controller;



import com.example.model.Dictionary_words;
import com.example.service.Dictionary_wordsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dictionary_words")
public class Dictionary_wordsController {
    private final Dictionary_wordsService service;

    public Dictionary_wordsController(Dictionary_wordsService service) {
        this.service = service;
    }

    @PostMapping
    public void createDictionary_words(@RequestParam int word_id,
                                       @RequestParam int dictionary_id) {
        service.createDictionary_words(word_id, dictionary_id);
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public Dictionary_words getDictionary_words(@PathVariable int id) {
            return service.getDictionary_words(id);
    }
}
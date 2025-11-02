package com.example.controller;

import com.example.model.Cards_words;
import com.example.service.Cards_wordsService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/cards_words")
public class Cards_wordsController {
    private final Cards_wordsService service;

    public Cards_wordsController(Cards_wordsService service) {
        this.service = service;
    }

    @PostMapping
    public void createCards_words(@RequestParam int user_id,
                                  @RequestParam int word_id,
                                  @RequestParam int dictionary_id,
                                  @RequestParam int study_lvl,
                                  @RequestParam LocalDate next_review) {
        service.createCards_words(user_id, word_id, dictionary_id, study_lvl, next_review);
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public Cards_words getCards_words(@PathVariable int id) {
        return service.getCards_words(id);
    }
}
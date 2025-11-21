package com.example.controller;

import com.example.dto.CardsWordsDto;
import com.example.service.CardsWordsService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/cards_words")
public class CardsWordsController {
    private final CardsWordsService service;

    public CardsWordsController(CardsWordsService service) {
        this.service = service;
    }

    @PostMapping
    public void createCardsWords(@RequestParam int user_id,
                                  @RequestParam int word_id,
                                  @RequestParam int dictionary_id,
                                  @RequestParam int study_lvl,
                                  @RequestParam LocalDateTime next_review) {
        service.createCardsWords(user_id, word_id, dictionary_id, study_lvl, next_review);
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public CardsWordsDto getCardsWords(@PathVariable int id) {
        return service.getCardsWords(id);
    }
}
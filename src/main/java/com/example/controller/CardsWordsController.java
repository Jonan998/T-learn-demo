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
    public void createCardsWords(@RequestParam (name = "user_id") int userId,
                                  @RequestParam (name = "word_id") int wordId,
                                  @RequestParam (name = "dictionary_id") int dictionaryId,
                                  @RequestParam (name = "study_lvl") int studyLvl,
                                  @RequestParam (name = "next_review") LocalDateTime nextReview) {
        service.createCardsWords(userId, wordId, dictionaryId, studyLvl, nextReview);
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public CardsWordsDto getCardsWords(@PathVariable int id) {
        return service.getCardsWords(id);
    }
}
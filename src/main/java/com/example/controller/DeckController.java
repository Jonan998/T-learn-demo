package com.example.controller;

import com.example.dto.WordDto;
import com.example.service.DeckService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learning/words")
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping(value ="/new", produces = "application/json; charset=UTF-8")
    public List<WordDto> getNewWords(@RequestParam("user_id") Integer userId) {
        return deckService.getNewDeck(userId);
    }

}

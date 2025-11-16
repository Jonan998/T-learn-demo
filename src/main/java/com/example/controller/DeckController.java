package com.example.controller;

import com.example.dto.DictionaryDto;
import com.example.dto.WordDto;
import com.example.service.DeckServiceImpl;
import com.example.service.DictionaryService;
import com.example.service.DictionaryServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learning")
public class DeckController {

    private final DeckServiceImpl deckService;
    private final DictionaryServiceImpl dictionaryService;

    public DeckController(DeckServiceImpl deckService, DictionaryServiceImpl dictionaryService) {
        this.deckService = deckService;
        this.dictionaryService = dictionaryService;
    }

    @GetMapping(value ="/words/new", produces = "application/json; charset=UTF-8")
    public List<WordDto> getNewWords(@RequestParam("user_id") Integer userId) {
        return deckService.getNewDeck(userId);
    }

    @GetMapping(value = "/words/repeat", produces = "application/json; charset=UTF-8")
    public List<WordDto> getRepeatWords(@RequestParam("user_id") Integer userId) {
        return deckService.getRepeatDeck(userId);
    }

    @GetMapping(value = "/dictionary", produces = "application/json; charset=UTF-8")
    public List<DictionaryDto> getUserDictionaries(@RequestParam("user_id") int userId){
        return dictionaryService.getUserDictionaries(userId);
    }

}

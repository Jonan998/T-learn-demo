package com.example.controller;

import com.example.dto.CardsWordsDto;
import com.example.dto.DictionaryDto;
import com.example.dto.WordDto;
import com.example.service.AuthService;
import com.example.service.CardsWordsServiceImpl;
import com.example.service.DeckServiceImpl;
import com.example.service.DictionaryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learning")
public class DeckController {

    private final DeckServiceImpl deckService;
    private final DictionaryServiceImpl dictionaryService;
    private final CardsWordsServiceImpl cardsWordsService;
    private final AuthService authService;

    public DeckController(DeckServiceImpl deckService, DictionaryServiceImpl dictionaryService,CardsWordsServiceImpl cardsWordsService, AuthService authService) {
        this.deckService = deckService;
        this.dictionaryService = dictionaryService;
        this.cardsWordsService = cardsWordsService;
        this.authService = authService;
    }

    @GetMapping(value ="/words/new", produces = "application/json; charset=UTF-8")
    public List<WordDto> getNewWords(HttpServletRequest request) {
        return deckService.getNewDeck(authService.getUserId(request));
    }

    @GetMapping(value = "/words/repeat", produces = "application/json; charset=UTF-8")
    public List<WordDto> getRepeatWords(HttpServletRequest request) {
        return deckService.getRepeatDeck(authService.getUserId(request));
    }

    @GetMapping(value = "/dictionary", produces = "application/json; charset=UTF-8")
    public List<DictionaryDto> getUserDictionaries(HttpServletRequest request){
        return dictionaryService.getUserDictionaries(authService.getUserId(request));
    }

    @PatchMapping(value = "/progress", produces = "application/json; charset=UTF-8")
    public void updateWordStatus(HttpServletRequest request,
                                 @RequestBody List<CardsWordsDto> updates) {
        cardsWordsService.updateWordStatus(authService.getUserId(request), updates);
    }

}

package com.example.controller;

import com.example.dto.CardsWordsDto;
import com.example.dto.DictionaryDto;
import com.example.dto.WordDto;
import com.example.service.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/learning")
public class DeckController {

  private final DeckService deckService;
  private final DictionaryService dictionaryService;
  private final CardsWordsService cardsWordsService;
  private final AuthService authService;

  public DeckController(
      DeckService deckService,
      DictionaryService dictionaryService,
      CardsWordsService cardsWordsService,
      AuthService authService) {
    this.deckService = deckService;
    this.dictionaryService = dictionaryService;
    this.cardsWordsService = cardsWordsService;
    this.authService = authService;
  }

  @GetMapping(value = "/words/new", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<WordDto> getNewWords(HttpServletRequest request) {
    return deckService.getNewDeck(authService.getUserId(request));
  }

  @GetMapping(value = "/words/repeat", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<WordDto> getRepeatWords(HttpServletRequest request) {
    return deckService.getRepeatDeck(authService.getUserId(request));
  }

  @GetMapping(value = "/dictionary", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<DictionaryDto> getUserDictionaries(HttpServletRequest request) {
    return dictionaryService.getUserDictionaries(authService.getUserId(request));
  }

  @PatchMapping(value = "/progress", produces = MediaType.APPLICATION_JSON_VALUE)
  public void updateWordStatus(
      HttpServletRequest request, @RequestBody List<CardsWordsDto> updates) {
    cardsWordsService.updateWordStatus(authService.getUserId(request), updates);
  }
}

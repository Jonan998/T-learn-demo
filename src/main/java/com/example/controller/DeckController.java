package com.example.controller;

import com.example.Security.UserPrincipal;
import com.example.dto.CardsWordsDto;
import com.example.dto.DictionaryDto;
import com.example.dto.WordDto;
import com.example.service.*;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
  public List<WordDto> getNewWords(@AuthenticationPrincipal UserPrincipal user) {
    return deckService.getNewDeck(user.getId());
  }

  @GetMapping(value = "/words/repeat", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<WordDto> getRepeatWords(@AuthenticationPrincipal UserPrincipal user) {
    return deckService.getRepeatDeck(user.getId());
  }

  @GetMapping(value = "/dictionary", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<DictionaryDto> getUserDictionaries(@AuthenticationPrincipal UserPrincipal user) {
    return dictionaryService.getUserDictionaries(user.getId());
  }

  @PatchMapping(value = "/progress", produces = MediaType.APPLICATION_JSON_VALUE)
  public void updateWordStatus(
      @AuthenticationPrincipal UserPrincipal user, @RequestBody List<CardsWordsDto> updates) {
    cardsWordsService.updateWordStatus(user.getId(), updates);
  }
}

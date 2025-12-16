package ru.teducation.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.teducation.Security.UserPrincipal;
import ru.teducation.dto.CardsWordsDto;
import ru.teducation.dto.DictionaryDto;
import ru.teducation.dto.WordDto;
import ru.teducation.service.CardsWordsService;
import ru.teducation.service.DeckService;
import ru.teducation.service.DictionaryService;

@RestController
@RequestMapping("/learning")
public class DeckController {

  private final DeckService deckService;
  private final DictionaryService dictionaryService;
  private final CardsWordsService cardsWordsService;

  private static final String NEW_WORDS_PATH = "/words/new";
  private static final String REPEAT_WORDS_PATH = "/words/repeat";
  private static final String DICTIONARY_PATH = "/dictionary";
  private static final String PROGRESS_PATH = "/progress";

  public DeckController(
      DeckService deckService,
      DictionaryService dictionaryService,
      CardsWordsService cardsWordsService) {
    this.deckService = deckService;
    this.dictionaryService = dictionaryService;
    this.cardsWordsService = cardsWordsService;
  }

  @GetMapping(value = NEW_WORDS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<WordDto> getNewWords(@AuthenticationPrincipal UserPrincipal user) {
    return deckService.getNewDeck(user.getId());
  }

  @GetMapping(value = REPEAT_WORDS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<WordDto> getRepeatWords(@AuthenticationPrincipal UserPrincipal user) {
    return deckService.getRepeatDeck(user.getId());
  }

  @GetMapping(value = DICTIONARY_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<DictionaryDto> getUserDictionaries(@AuthenticationPrincipal UserPrincipal user) {
    return dictionaryService.getUserDictionaries(user.getId());
  }

  @PatchMapping(value = PROGRESS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  public void updateWordStatus(
      @AuthenticationPrincipal UserPrincipal user, @RequestBody List<CardsWordsDto> updates) {
    cardsWordsService.updateWordStatus(user.getId(), updates);
  }
}

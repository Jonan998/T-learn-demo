package ru.teducation.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.teducation.Security.UserPrincipal;
import ru.teducation.dto.DictionaryDto;
import ru.teducation.dto.DictionaryWordsDto;
import ru.teducation.dto.WordDto;
import ru.teducation.service.DictionaryService;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
  private final DictionaryService service;

  private static final String CREATE_PATH = "/create";
<<<<<<< HEAD
  private static final String ADD_WORD = "/add";
  private static final String SEARCH_WORD = "/search";
=======
>>>>>>> 8aa8c79 (custom-dictionary)

  public DictionaryController(DictionaryService service) {
    this.service = service;
  }

  @PostMapping
  public void createDictionary(
      @RequestParam String name, @RequestParam String description, @RequestParam String language) {
    service.createDictionary(name, description, language);
  }

  @GetMapping(value = "/{id:\\\\d+}", produces = "application/json; charset=UTF-8")
  public DictionaryDto getDictionary(@PathVariable int id) {
    return service.getDictionary(id);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<WordDto> getWordsByDictionaryId(@AuthenticationPrincipal UserPrincipal user) {
    return service.getWordsByDictionaryId(user.getId());
  }

  @PostMapping(value = CREATE_PATH)
  @ResponseStatus(HttpStatus.CREATED)
  public void createCustomDictionary(
      @Valid @RequestBody DictionaryDto dictionary, @AuthenticationPrincipal UserPrincipal user) {
    service.createCustomDictionary(dictionary, user.getId());
  }
<<<<<<< HEAD

  @PostMapping(value = ADD_WORD)
  @ResponseStatus(HttpStatus.CREATED)
  public void addNewWord(
      @AuthenticationPrincipal UserPrincipal user,
      @RequestBody DictionaryWordsDto dictionaryWords) {
    service.addNewWord(user.getId(), dictionaryWords);
  }

  @GetMapping(value = SEARCH_WORD, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<WordDto> searchWord(@RequestParam String prefix) {
    return service.searchWord(prefix);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteDictionary(@PathVariable int id, @AuthenticationPrincipal UserPrincipal user) {

    service.deleteDictionary(user.getId(), id);
  }

  @DeleteMapping("/{dictionaryId}/word/{wordId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeWord(
      @PathVariable int dictionaryId,
      @PathVariable int wordId,
      @AuthenticationPrincipal UserPrincipal user) {

    service.removeWord(user.getId(), dictionaryId, wordId);
  }
=======
>>>>>>> 8aa8c79 (custom-dictionary)
}

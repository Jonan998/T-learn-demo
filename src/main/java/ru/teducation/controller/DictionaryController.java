package ru.teducation.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
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
  private static final String CREATE_PATH = "/create";
  private static final String ADD_WORD = "/add";
  private static final String SEARCH_WORD = "/search";
  private final DictionaryService service;

  public DictionaryController(DictionaryService service) {
    this.service = service;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Map<String, Object>> getWordsByDictionaryId(
      @AuthenticationPrincipal UserPrincipal user) {
    return service.getWordsByDictionaryId(user.getId());
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DictionaryDto getDictionary(@PathVariable Integer id) {
    return service.getDictionary(id);
  }

  @PostMapping(value = CREATE_PATH)
  @ResponseStatus(HttpStatus.CREATED)
  public void createCustomDictionary(
      @Valid @RequestBody DictionaryDto dictionary, @AuthenticationPrincipal UserPrincipal user) {
    service.createCustomDictionary(dictionary, user.getId());
  }

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
}

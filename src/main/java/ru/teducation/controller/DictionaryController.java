package ru.teducation.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.teducation.Security.UserPrincipal;
import ru.teducation.dto.DictionaryDto;
import ru.teducation.dto.WordDto;
import ru.teducation.service.DictionaryService;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
  private final DictionaryService service;

  public DictionaryController(DictionaryService service) {
    this.service = service;
  }

  @PostMapping
  public void createDictionary(
      @RequestParam String name, @RequestParam String description, @RequestParam String language) {
    service.createDictionary(name, description, language);
  }

  @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
  public DictionaryDto getDictionary(@PathVariable int id) {
    return service.getDictionary(id);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<WordDto> getWordsByDictionaryId(@AuthenticationPrincipal UserPrincipal user) {
    return service.getWordsByDictionaryId(user.getId());
  }
}

package ru.teducation.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.teducation.dto.DictionaryWordsDto;
import ru.teducation.service.DictionaryWordsService;

@RestController
@RequestMapping("/dictionary_words")
public class DictionaryWordsController {
  private final DictionaryWordsService service;

  public DictionaryWordsController(DictionaryWordsService service) {
    this.service = service;
  }

  @PostMapping
  public void createDictionary_words(@RequestParam int word_id, @RequestParam int dictionary_id) {
    service.createDictionaryWords(word_id, dictionary_id);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DictionaryWordsDto getDictionary_words(@PathVariable int id) {
    return service.getDictionaryWords(id);
  }
}

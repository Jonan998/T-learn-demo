package ru.teducation.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.teducation.dto.WordDto;
import ru.teducation.service.WordService;

@RestController
@RequestMapping("/words")
public class WordController {
  private final WordService service;

  public WordController(WordService service) {
    this.service = service;
  }

  @PostMapping
  public void createWord(
      @RequestParam String eng, @RequestParam String rus, @RequestParam String transcription) {
    service.createWord(eng, rus, transcription);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public WordDto getWord(@PathVariable int id) {
    return service.getWord(id);
  }

  @GetMapping(value = "/random/{count}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<WordDto> getRandWords(@PathVariable("count") int count) {
    return service.getRandWords(count);
  }
}

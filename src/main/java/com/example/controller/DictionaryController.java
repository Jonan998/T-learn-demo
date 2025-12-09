package com.example.controller;

import com.example.dto.DictionaryDto;
import com.example.dto.WordDto;
import com.example.service.AuthService;
import com.example.service.DictionaryService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
  private final DictionaryService service;
  private final AuthService authService;

  public DictionaryController(DictionaryService service, AuthService authService) {
    this.service = service;
    this.authService = authService;
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
  public List<WordDto> getWordsByDictionaryId(HttpServletRequest request) {
    return service.getWordsByDictionaryId(authService.getUserId(request));
  }
}

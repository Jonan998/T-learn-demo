package ru.teducation.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.teducation.dto.UsersDictionariesDto;
import ru.teducation.service.UsersDictionariesService;

@RestController
@RequestMapping("/users_dictionaries")
public class UsersDictionariesController {
  private final UsersDictionariesService service;

  public UsersDictionariesController(UsersDictionariesService service) {
    this.service = service;
  }

  @PostMapping
  public void createUsers_dictionaries(
      @RequestParam int user_id,
      @RequestParam int dictionary_id,
      @RequestParam String is_active,
      @RequestParam int progress) {
    service.createUsersDictionaries(user_id, dictionary_id, is_active, progress);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UsersDictionariesDto getUsers_dictionaries(@PathVariable int id) {
    return service.getUsersDictionaries(id);
  }
}

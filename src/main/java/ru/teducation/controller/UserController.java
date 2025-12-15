package ru.teducation.controller;

import java.time.LocalDate;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.teducation.Security.UserPrincipal;
import ru.teducation.dto.UserDto;
import ru.teducation.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

  private static final String ID_PATH = "/{id}";
  private static final String SETTING_PATH = "/settings";

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @PostMapping
  public void createUser(
      @RequestParam String name,
      @RequestParam String password,
      @RequestParam(name = "created_at_new") LocalDate createdAtNew,
      @RequestParam(name = "created_at_repeat") LocalDate createdAtRepeat,
      @RequestParam(name = "limit_new") int limitNew,
      @RequestParam(name = "limit_repeat") int limitRepeat) {

    service.createUser(name, password, createdAtNew, createdAtRepeat, limitNew, limitRepeat);
  }

  @GetMapping(value = ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto getUser(@PathVariable int id) {
    return service.getUser(id);
  }

  @GetMapping(value = SETTING_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto getUserLimits(@AuthenticationPrincipal UserPrincipal user) {
    return service.getUserLimits(user.getId());
  }

  @PatchMapping(value = SETTING_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  public void updateUserSettings(
      @AuthenticationPrincipal UserPrincipal user, @RequestBody UserDto dto) {
    service.updateUserSettings(user.getId(), dto);
  }
}

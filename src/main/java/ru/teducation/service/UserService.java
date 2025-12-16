package ru.teducation.service;

import java.time.LocalDate;
import ru.teducation.dto.UserDto;

public interface UserService {
  UserDto getUser(int userId);

  void createUser(
      String name,
      String password,
      LocalDate createdAtNew,
      LocalDate createdAtRepeat,
      int limitNew,
      int limitRepeat);

  UserDto getUserLimits(int userId);

  void updateUserSettings(int userId, UserDto dto);
}

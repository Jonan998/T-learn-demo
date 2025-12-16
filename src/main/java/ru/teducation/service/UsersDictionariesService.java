package ru.teducation.service;

import ru.teducation.dto.UsersDictionariesDto;

public interface UsersDictionariesService {
  UsersDictionariesDto getUsersDictionaries(int usersDictionariesId);

  void createUsersDictionaries(int userId, int dictionaryId, String isActive, int progress);
}

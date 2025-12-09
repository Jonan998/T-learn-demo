package com.example.service;

import com.example.dto.UsersDictionariesDto;

public interface UsersDictionariesService {
  UsersDictionariesDto getUsersDictionaries(int usersDictionariesId);

  void createUsersDictionaries(int userId, int dictionaryId, String isActive, int progress);
}

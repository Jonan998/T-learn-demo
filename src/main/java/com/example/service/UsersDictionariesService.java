package com.example.service;

import com.example.dto.UsersDictionariesDto;
import com.example.model.UsersDictionaries;

public interface UsersDictionariesService {
    UsersDictionariesDto getUsersDictionaries(int usersDictionariesId);
    void createUsersDictionaries(int userId, int dictionaryId, String isActive, int progress);
}

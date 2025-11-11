package com.example.service;

import com.example.dto.UsersDictionariesDto;
import com.example.model.UsersDictionaries;

public interface UsersDictionariesService {
    UsersDictionariesDto getUsersDictionaries(int users_dictionariesId);
    void createUsersDictionaries(int user_id, int dictionary_id, String is_active, int progress);
}

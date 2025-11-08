package com.example.service;

import com.example.model.UsersDictionaries;

public interface UsersDictionariesService {
    UsersDictionaries getUsersDictionaries(int users_dictionariesId);
    void createUsersDictionaries(int user_id, int dictionary_id, String is_active, int progress);
}

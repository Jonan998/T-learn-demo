package com.example.service;

import com.example.model.Dictionary;
import com.example.model.User;
import com.example.model.Users_dictionaries;

public interface Users_dictionariesService {
    Users_dictionaries getUsers_dictionaries(int users_dictionariesId);
    void createUsers_dictionaries(int user_id, int dictionary_id, String is_active, int progress);
}

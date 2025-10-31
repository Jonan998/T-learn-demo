package com.example.service;

import com.example.model.Dictionary;

public interface DictionaryService {
    Dictionary getDictionary(int dictionaryId);
    void createDictionary(String name, String description, String language);
}

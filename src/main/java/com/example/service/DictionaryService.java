package com.example.service;

import com.example.dto.DictionaryDto;
import com.example.model.Dictionary;

public interface DictionaryService {
    DictionaryDto getDictionary(int dictionaryId);
    void createDictionary(String name, String description, String language);
}

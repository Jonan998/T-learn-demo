package com.example.service;

import com.example.dto.DictionaryDto;
import com.example.model.Dictionary;

import java.util.List;

public interface DictionaryService {
    DictionaryDto getDictionary(int dictionaryId);
    void createDictionary(String name, String description, String language);
    List<DictionaryDto> getUserDictionaries(int userId);
}

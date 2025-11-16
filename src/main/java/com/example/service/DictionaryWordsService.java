package com.example.service;

import com.example.dto.DictionaryWordsDto;
import com.example.model.DictionaryWords;

public interface DictionaryWordsService {
    DictionaryWordsDto getDictionaryWords(int dictionaryWordsId);
    void createDictionaryWords(int wordId, int dictionaryId);
}
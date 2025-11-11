package com.example.service;

import com.example.dto.DictionaryWordsDto;
import com.example.model.DictionaryWords;

public interface DictionaryWordsService {
    DictionaryWordsDto getDictionaryWords(int dictionary_wordsId);
    void createDictionaryWords(int word_id, int dictionary_id);
}
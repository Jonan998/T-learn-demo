package com.example.service;

import com.example.model.DictionaryWords;

public interface DictionaryWordsService {
    DictionaryWords getDictionaryWords(int dictionary_wordsId);
    void createDictionaryWords(int word_id, int dictionary_id);
}
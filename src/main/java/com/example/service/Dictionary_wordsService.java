package com.example.service;

import com.example.model.Dictionary_words;

public interface Dictionary_wordsService {
    Dictionary_words getDictionary_words(int dictionary_wordsId);
    void createDictionary_words(int word_id, int dictionary_id);
}
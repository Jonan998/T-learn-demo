package com.example.service;

import com.example.model.Dictionary_words;
import com.example.model.User;
import com.example.model.Word;

public interface Dictionary_wordsService {
    Dictionary_words getDictionary_words(int dictionary_wordsId);
    void createDictionary_words(int word_id,int dictionary_id);
}

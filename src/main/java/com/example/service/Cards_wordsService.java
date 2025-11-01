package com.example.service;

import com.example.model.Cards_words;
import java.time.LocalDate;

public interface Cards_wordsService {
    Cards_words getCards_words(int cards_wordsid);
    void createCards_words(int user_id, int word_id, int dictionary_id, int study_lvl, LocalDate next_review);
}

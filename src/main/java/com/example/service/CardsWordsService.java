package com.example.service;

import com.example.dto.CardsWordsDto;
import com.example.model.CardsWords;
import java.time.LocalDate;

public interface CardsWordsService {
    CardsWordsDto getCardsWords(int cards_wordsid);
    void createCardsWords(int user_id, int word_id, int dictionary_id, int study_lvl, LocalDate next_review);
}

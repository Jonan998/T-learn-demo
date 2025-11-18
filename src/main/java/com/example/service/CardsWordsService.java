package com.example.service;

import com.example.dto.CardsWordsDto;
import com.example.model.CardsWords;
import java.time.LocalDate;

public interface CardsWordsService {
    CardsWordsDto getCardsWords(int cardsWordsid);
    void createCardsWords(int userId, int wordIid, int dictionaryId, int studyLvl, LocalDate nextReview);
}

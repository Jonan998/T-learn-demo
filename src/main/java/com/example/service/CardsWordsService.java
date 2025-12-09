package com.example.service;

import com.example.dto.CardsWordsDto;
import java.time.LocalDateTime;
import java.util.List;

public interface CardsWordsService {
  CardsWordsDto getCardsWords(int cardsWordsid);

  void createCardsWords(
      int userId, int wordIid, int dictionaryId, int studyLvl, LocalDateTime nextReview);

  void updateWordStatus(int userId, List<CardsWordsDto> updates);
}

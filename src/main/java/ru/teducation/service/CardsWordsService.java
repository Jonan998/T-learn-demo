package ru.teducation.service;

import java.time.LocalDateTime;
import java.util.List;
import ru.teducation.dto.CardsWordsDto;

public interface CardsWordsService {
  CardsWordsDto getCardsWords(int cardsWordsid);

  void createCardsWords(
      int userId, int wordIid, int dictionaryId, int studyLvl, LocalDateTime nextReview);

  void updateWordStatus(int userId, List<CardsWordsDto> updates);
}

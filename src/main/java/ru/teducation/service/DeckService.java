package ru.teducation.service;

import java.util.List;
import ru.teducation.dto.NewDeckDto;
import ru.teducation.dto.WordDto;

public interface DeckService {
  List<NewDeckDto> getNewDeck(int userId);

  List<WordDto> getRepeatDeck(int userId);
}

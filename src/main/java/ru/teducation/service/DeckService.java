package ru.teducation.service;

import java.util.List;
import ru.teducation.dto.WordDto;

public interface DeckService {
  List<WordDto> getNewDeck(int userId);

  List<WordDto> getRepeatDeck(int userId);
}

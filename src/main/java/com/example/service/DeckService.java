package com.example.service;

import com.example.dto.WordDto;
import java.util.List;

public interface DeckService {
  List<WordDto> getNewDeck(int userId);

  List<WordDto> getRepeatDeck(int userId);
}

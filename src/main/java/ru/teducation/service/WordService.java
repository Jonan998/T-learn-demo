package ru.teducation.service;

import java.util.List;
import ru.teducation.dto.WordDto;

public interface WordService {
  WordDto getWord(int wordId);

  void createWord(String eng, String rus, String transcription);

  List<WordDto> getRandWords(int limit);
}

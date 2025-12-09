package com.example.service;

import com.example.dto.WordDto;
import java.util.List;

public interface WordService {
  WordDto getWord(int wordId);

  void createWord(String eng, String rus, String transcription);

  List<WordDto> getRandWords(int limit);
}

package ru.teducation.service;

import ru.teducation.dto.DictionaryWordsDto;

public interface DictionaryWordsService {
  DictionaryWordsDto getDictionaryWords(int dictionaryWordsId);

  void createDictionaryWords(int wordId, int dictionaryId);
}

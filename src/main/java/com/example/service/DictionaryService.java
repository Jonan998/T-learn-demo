package com.example.service;

import com.example.dto.DictionaryDto;
import com.example.dto.WordDto;
import java.util.List;

public interface DictionaryService {
  DictionaryDto getDictionary(int dictionaryId);

  void createDictionary(String name, String description, String language);

  List<DictionaryDto> getUserDictionaries(int userId);

  List<WordDto> getWordsByDictionaryId(int dictionaryId);
}

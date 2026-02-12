package ru.teducation.service;

import java.util.List;
import ru.teducation.dto.DictionaryDto;
import ru.teducation.dto.WordDto;

public interface DictionaryService {
  DictionaryDto getDictionary(int dictionaryId);

  void createDictionary(String name, String description, String language);

  List<DictionaryDto> getUserDictionaries(int userId);

  List<WordDto> getWordsByDictionaryId(int dictionaryId);

  void createCustomDictionary(DictionaryDto dictionary, Integer userId);
}

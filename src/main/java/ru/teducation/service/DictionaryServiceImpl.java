package ru.teducation.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.teducation.dto.DictionaryDto;
import ru.teducation.dto.WordDto;
import ru.teducation.mapper.DictionaryMapper;
import ru.teducation.model.Dictionary;
import ru.teducation.repository.DictionaryRepository;
import ru.teducation.repository.WordRepository;

@Slf4j
@Service
public class DictionaryServiceImpl implements DictionaryService {
  private final DictionaryRepository repository;
  private final DictionaryMapper dictionaryMapper;
  private final WordRepository wordRepository;
  ;

  public DictionaryServiceImpl(
      DictionaryRepository repository,
      DictionaryMapper dictionaryMapper,
      WordRepository wordRepository) {
    this.repository = repository;
    this.dictionaryMapper = dictionaryMapper;
    this.wordRepository = wordRepository;
  }

  @Override
  public DictionaryDto getDictionary(int dictionaryId) {
    Dictionary dictionary = repository.findById(dictionaryId).orElse(null);
    return dictionary != null ? dictionaryMapper.toDto(dictionary) : null;
  }

  @Override
  public void createDictionary(String name, String description, String language) {
    repository.save(new Dictionary(name, description, language));
  }

  public DictionaryDto createDictionary(DictionaryDto dictionaryDto) {
    Dictionary dictionary =
        new Dictionary(
            dictionaryDto.getName(), dictionaryDto.getDescription(), dictionaryDto.getLanguage());
    Dictionary savedDictionary = repository.save(dictionary);
    return dictionaryMapper.toDto(savedDictionary);
  }

  @Override
  public List<DictionaryDto> getUserDictionaries(int userId) {
    log.info("Получение словарей для userId={}", userId);

    List<DictionaryDto> dictionaries = repository.findUserDictionaries(userId);

    log.debug("Найдено {} словарей для userId={}", dictionaries.size(), userId);

    return dictionaries;
  }

  @Override
  public List<WordDto> getWordsByDictionaryId(int dictionaryId) {
    log.info("Получение слов по dictionaryId={}", dictionaryId);

    List<WordDto> words = wordRepository.findWordsByDictionaryId(dictionaryId);

    log.debug("Найдено {} слов в dictionaryId={}", words.size(), dictionaryId);

    return words;
  }
}

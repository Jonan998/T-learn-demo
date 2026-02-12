package ru.teducation.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.teducation.dto.DictionaryDto;
import ru.teducation.dto.DictionaryWordsDto;
import ru.teducation.dto.WordDto;
import ru.teducation.exception.ConflictException;
import ru.teducation.exception.NotFoundException;
import ru.teducation.mapper.DictionaryMapper;
import ru.teducation.mapper.DictionaryWordsMapper;
import ru.teducation.model.Dictionary;
import ru.teducation.model.DictionaryWords;
import ru.teducation.model.User;
import ru.teducation.model.Word;
import ru.teducation.repository.DictionaryRepository;
import ru.teducation.repository.DictionaryWordsRepository;
import ru.teducation.repository.UserRepository;
import ru.teducation.repository.WordRepository;

@Slf4j
@Service
public class DictionaryServiceImpl implements DictionaryService {
  private final DictionaryRepository repository;
  private final DictionaryMapper dictionaryMapper;
  private final WordRepository wordRepository;
  private final UserRepository userRepository;
  private final DictionaryWordsMapper dictionaryWordsMapper;
  private final DictionaryWordsRepository dictionaryWordsRepository;


  public DictionaryServiceImpl(
      DictionaryRepository repository,
      DictionaryMapper dictionaryMapper,
      WordRepository wordRepository,
      UserRepository userRepository,
      DictionaryWordsMapper dictionaryWordsMapper,
      DictionaryWordsRepository dictionaryWordsRepository) {
    this.repository = repository;
    this.dictionaryMapper = dictionaryMapper;
    this.wordRepository = wordRepository;
    this.userRepository = userRepository;
    this.dictionaryWordsMapper = dictionaryWordsMapper;
    this.dictionaryWordsRepository = dictionaryWordsRepository;
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

  @Override
  public void createCustomDictionary(DictionaryDto dictionary, Integer userId) {
    User owner =
        userRepository
            .findById(userId)
            .orElseThrow(
                () -> {
                  log.warn("Пользователь {} не найден", userId);
                  return new NotFoundException("Пользователь не найден");
                });

    if (repository.existsByNameAndOwnerId(dictionary.getName(), owner)) {
      log.warn(
          "Попытка создать дубликат словаря name='{}' для userId={}", dictionary.getName(), userId);
      throw new ConflictException("Словарь с таким именем уже есть");
    }

    Dictionary customDictionary = dictionaryMapper.toEntity(dictionary, owner);
    repository.save(customDictionary);

    log.info(
        "Создан кастомный словарь name='{}', ownerId={}, isPublic={}",
        customDictionary.getName(),
        owner.getId(),
        customDictionary.getIsPublic());
  }

  @Override
  public List<String> searchWord(String prefix) {
    return wordRepository.searchWord(prefix);
  }

  @Transactional
  @Override
  public void addNewWord(Integer userId, DictionaryWordsDto dictionaryWords) {
      int wordId = dictionaryWords.getWordId();
      int dictionaryId = dictionaryWords.getDictionaryId();

      Word word = wordRepository.findById(wordId).orElseThrow(
              () -> {
                  throw new NotFoundException("Слово не найдено");
              }
      );

      Dictionary dictionary = repository.findById(dictionaryId).orElseThrow(
              () -> {
                  throw new NotFoundException("Словарь не найдено");
              }
      );

      if (!dictionary.getOwnerId().equals(userId)) {
          throw new ConflictException("Нет прав");
      }

      if (dictionaryWordsRepository.existsByDictionaryIdAndWordId(dictionaryId,wordId)){
          throw new ConflictException("Такое слово уже есть в словаре");
      }

      DictionaryWords newWord = new DictionaryWords();
      newWord.setWord(word);
      newWord.setDictionary(dictionary);

      dictionaryWordsRepository.save(newWord);
  }
}

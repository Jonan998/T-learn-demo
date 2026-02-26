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
import ru.teducation.mapper.WordMapper;
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
  ;
  private final DictionaryWordsRepository dictionaryWordsRepository;
  private final WordMapper wordMapper;

  public DictionaryServiceImpl(
      DictionaryRepository repository,
      DictionaryMapper dictionaryMapper,
      WordRepository wordRepository,
      UserRepository userRepository,
      DictionaryWordsRepository dictionaryWordsRepository,
      WordMapper wordMapper) {
    this.repository = repository;
    this.dictionaryMapper = dictionaryMapper;
    this.wordRepository = wordRepository;
    this.userRepository = userRepository;
    this.dictionaryWordsRepository = dictionaryWordsRepository;
    this.wordMapper = wordMapper;
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

    if (repository.existsByNameAndOwner_Id(dictionary.getName(), owner.getId())) {
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
  public List<WordDto> searchWord(String prefix) {
    List<Word> words = wordRepository.findTop10ByEngLangStartingWithIgnoreCase(prefix);

    return wordMapper.toDtoList(words);
  }

  @Transactional
  @Override
  public void addNewWord(Integer userId, DictionaryWordsDto dictionaryWords) {
    int wordId = dictionaryWords.getWordId();
    int dictionaryId = dictionaryWords.getDictionaryId();

    Word word =
        wordRepository
            .findById(wordId)
            .orElseThrow(() -> new NotFoundException("Слово не найдено"));

    Dictionary dictionary =
        repository
            .findById(dictionaryId)
            .orElseThrow(() -> new NotFoundException("Словарь не найден"));

    User owner = dictionary.getOwner();
    if (owner == null || !owner.getId().equals(userId)) {
      throw new ConflictException("Нет прав");
    }

    if (dictionaryWordsRepository.existsByDictionaryIdAndWordId(dictionaryId, wordId)) {
      throw new ConflictException("Такое слово уже есть в словаре");
    }

    DictionaryWords newWord = new DictionaryWords();
    newWord.setWord(word);
    newWord.setDictionary(dictionary);

    dictionaryWordsRepository.save(newWord);
  }

  @Transactional
  @Override
  public void deleteDictionary(Integer userId, int dictionaryId) {

    Dictionary dictionary =
        repository
            .findById(dictionaryId)
            .orElseThrow(() -> new NotFoundException("Словарь не найден"));

    User owner = dictionary.getOwner();
    if (owner == null || !owner.getId().equals(userId)) {
      throw new ConflictException("Нет прав");
    }

    repository.delete(dictionary);
  }

  @Transactional
  @Override
  public void removeWord(Integer userId, int dictionaryId, int wordId) {

    Dictionary dictionary =
        repository
            .findById(dictionaryId)
            .orElseThrow(() -> new NotFoundException("Словарь не найден"));

    User owner = dictionary.getOwner();
    if (owner == null || !owner.getId().equals(userId)) {
      throw new ConflictException("Нет прав");
    }

    DictionaryWords relation =
        dictionaryWordsRepository
            .findByDictionaryIdAndWordId(dictionaryId, wordId)
            .orElseThrow(() -> new NotFoundException("Слово не найдено в словаре"));

    dictionaryWordsRepository.delete(relation);
  }
}

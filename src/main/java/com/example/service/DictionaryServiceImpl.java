package com.example.service;

import com.example.dto.DictionaryDto;
import com.example.dto.WordDto;
import com.example.exception.AuthenticationException;
import com.example.exception.TooManyRequestException;
import com.example.mapper.DictionaryMapper;
import com.example.model.Dictionary;
import com.example.repository.DictionaryRepository;
import com.example.repository.WordRepository;
import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DictionaryServiceImpl implements DictionaryService {
  private final DictionaryRepository repository;
  private final DictionaryMapper dictionaryMapper;
  private final WordRepository wordRepository;
  private final RateLimitService rateLimitService;

  public DictionaryServiceImpl(
      DictionaryRepository repository,
      DictionaryMapper dictionaryMapper,
      WordRepository wordRepository,
      RateLimitService rateLimitService) {
    this.repository = repository;
    this.dictionaryMapper = dictionaryMapper;
    this.wordRepository = wordRepository;
    this.rateLimitService = rateLimitService;
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

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      throw new AuthenticationException("Требуется авторизация");
    }

    if (rateLimitService.isRateLimitExceeded(userId, 10, Duration.ofMinutes(1))) {
      throw new TooManyRequestException("Слишком много запросов", 15);
    }

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

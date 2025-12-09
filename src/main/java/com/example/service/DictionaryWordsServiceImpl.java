package com.example.service;

import com.example.dto.DictionaryWordsDto;
import com.example.mapper.DictionaryWordsMapper;
import com.example.model.Dictionary;
import com.example.model.DictionaryWords;
import com.example.model.Word;
import com.example.repository.DictionaryRepository;
import com.example.repository.DictionaryWordsRepository;
import com.example.repository.WordRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DictionaryWordsServiceImpl implements DictionaryWordsService {
  private final DictionaryWordsRepository dictionaryWordsRepository;
  private final WordRepository wordRepository;
  private final DictionaryRepository dictionaryRepository;
  private final DictionaryWordsMapper dictionaryWordsMapper;

  public DictionaryWordsServiceImpl(
      DictionaryWordsRepository dictionaryWordsRepository,
      WordRepository wordRepository,
      DictionaryRepository dictionaryRepository,
      DictionaryWordsMapper dictionaryWordsMapper) {
    this.dictionaryWordsRepository = dictionaryWordsRepository;
    this.wordRepository = wordRepository;
    this.dictionaryRepository = dictionaryRepository;
    this.dictionaryWordsMapper = dictionaryWordsMapper;
  }

  @Override
  @Transactional
  public void createDictionaryWords(int wordId, int dictionaryId) {
    Optional<Word> word = wordRepository.findById(wordId);
    Optional<Dictionary> dictionary = dictionaryRepository.findById(dictionaryId);

    DictionaryWords dictionaryWords = new DictionaryWords(word.get(), dictionary.get());
    dictionaryWordsRepository.save(dictionaryWords);
  }

  @Override
  @Transactional(readOnly = true)
  public DictionaryWordsDto getDictionaryWords(int id) {
    DictionaryWords dictionaryWords = dictionaryWordsRepository.findByIdDict(id).orElse(null);
    return dictionaryWords != null ? dictionaryWordsMapper.toDto(dictionaryWords) : null;
  }

  @Transactional
  public DictionaryWordsDto createDictionaryWords(DictionaryWordsDto dictionaryWordsDto) {
    Optional<Word> word = wordRepository.findById(dictionaryWordsDto.getWordId());
    Optional<Dictionary> dictionary =
        dictionaryRepository.findById(dictionaryWordsDto.getDictionaryId());

    if (word.isPresent() && dictionary.isPresent()) {
      DictionaryWords dictionaryWords = new DictionaryWords(word.get(), dictionary.get());
      DictionaryWords savedDictionaryWords = dictionaryWordsRepository.save(dictionaryWords);
      return dictionaryWordsMapper.toDto(savedDictionaryWords);
    }
    return null;
  }
}

package com.example.service;

import com.example.dto.WordDto;
import com.example.model.Dictionary;
import com.example.dto.DictionaryDto;
import com.example.mapper.DictionaryMapper;
import com.example.repository.DictionaryRepository;
import com.example.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryRepository repository;
    private final DictionaryMapper dictionaryMapper;
    private final WordRepository wordRepository;

    public DictionaryServiceImpl(DictionaryRepository repository, DictionaryMapper dictionaryMapper, WordRepository wordRepository) {
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
        Dictionary dictionary = new Dictionary(
            dictionaryDto.getName(), 
            dictionaryDto.getDescription(), 
            dictionaryDto.getLanguage()
        );
        Dictionary savedDictionary = repository.save(dictionary);
        return dictionaryMapper.toDto(savedDictionary);
    }

    @Override
    public List<DictionaryDto> getUserDictionaries(int userId) {
        return repository.findUserDictionaries(userId);
    }

    @Override
    public List<WordDto> getWordsByDictionaryId(int dictionaryId){
        return wordRepository.findWordsByDictionaryId(dictionaryId);
    }

}
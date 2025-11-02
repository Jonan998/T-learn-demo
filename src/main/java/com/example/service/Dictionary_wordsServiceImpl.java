package com.example.service;

import com.example.model.Dictionary_words;
import com.example.model.Dictionary;
import com.example.model.Word;
import com.example.repository.DictionaryRepository;
import com.example.repository.Dictionary_wordsRepository;
import com.example.repository.WordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class Dictionary_wordsServiceImpl implements Dictionary_wordsService{
    private final Dictionary_wordsRepository repository;
    private final DictionaryRepository dictionaryRepository;
    private final WordRepository wordRepository;
    public Dictionary_wordsServiceImpl(Dictionary_wordsRepository repository,DictionaryRepository dictionaryRepository,WordRepository wordRepository){
        this.repository = repository;
        this.dictionaryRepository = dictionaryRepository;
        this.wordRepository = wordRepository;
    }

    @Override
    public Dictionary_words getDictionary_words(int dictionary_wordsId){
        return repository.findById(dictionary_wordsId).orElse(null);
    }
    @Override
    @Transactional
    public void createDictionary_words(int word_id,int dictionary_id){
        Optional<Word> word = wordRepository.findById(word_id);
        Optional<Dictionary> dictionary = dictionaryRepository.findById(dictionary_id);
        repository.save(new Dictionary_words(word.get(),dictionary.get()));
    }
}

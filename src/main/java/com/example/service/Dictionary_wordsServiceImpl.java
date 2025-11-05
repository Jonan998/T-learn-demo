package com.example.service;

import com.example.model.Dictionary_words;
import com.example.model.Word;
import com.example.model.Dictionary;
import com.example.repository.Dictionary_wordsRepository;
import com.example.repository.WordRepository;
import com.example.repository.DictionaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class Dictionary_wordsServiceImpl implements Dictionary_wordsService {
    private final Dictionary_wordsRepository dictionaryWordsRepository;
    private final WordRepository wordRepository;
    private final DictionaryRepository dictionaryRepository;

    public Dictionary_wordsServiceImpl(Dictionary_wordsRepository dictionaryWordsRepository,
                                       WordRepository wordRepository,
                                       DictionaryRepository dictionaryRepository) {
        this.dictionaryWordsRepository = dictionaryWordsRepository;
        this.wordRepository = wordRepository;
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    @Transactional
    public void createDictionary_words(int word_id, int dictionary_id) {
        Optional<Word> word = wordRepository.findById(word_id);
        Optional<Dictionary> dictionary = dictionaryRepository.findById(dictionary_id);
        Dictionary_words dictionaryWords = new Dictionary_words(word.get(), dictionary.get());
        dictionaryWordsRepository.save(dictionaryWords);

    }

    @Override
    @Transactional(readOnly = true)
    public Dictionary_words getDictionary_words(int id) {
        return dictionaryWordsRepository.findByIdDict(id).orElse(null);
    }
}
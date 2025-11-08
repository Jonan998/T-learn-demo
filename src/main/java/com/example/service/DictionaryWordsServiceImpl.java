package com.example.service;

import com.example.model.DictionaryWords;
import com.example.model.Word;
import com.example.model.Dictionary;
import com.example.repository.DictionaryWordsRepository;
import com.example.repository.WordRepository;
import com.example.repository.DictionaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DictionaryWordsServiceImpl implements DictionaryWordsService {
    private final DictionaryWordsRepository dictionaryWordsRepository;
    private final WordRepository wordRepository;
    private final DictionaryRepository dictionaryRepository;

    public DictionaryWordsServiceImpl(DictionaryWordsRepository dictionaryWordsRepository,
                                      WordRepository wordRepository,
                                      DictionaryRepository dictionaryRepository) {
        this.dictionaryWordsRepository = dictionaryWordsRepository;
        this.wordRepository = wordRepository;
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    @Transactional
    public void createDictionaryWords(int word_id,
                                      int dictionary_id) {
        Optional<Word> word = wordRepository.findById(word_id);
        Optional<Dictionary> dictionary = dictionaryRepository.findById(dictionary_id);

        DictionaryWords dictionaryWords = new DictionaryWords(word.get(), dictionary.get());
        dictionaryWordsRepository.save(dictionaryWords);
    }

    @Override
    @Transactional(readOnly = true)
    public DictionaryWords getDictionaryWords(int id) {
        return dictionaryWordsRepository.findByIdDict(id).orElse(null);
    }
}
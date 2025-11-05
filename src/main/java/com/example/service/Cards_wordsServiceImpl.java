package com.example.service;

import com.example.model.*;
import com.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class Cards_wordsServiceImpl implements Cards_wordsService {
    private final Cards_wordsRepository cardsWordsRepository;
    private final UserRepository userRepository;
    private final WordRepository wordRepository;
    private final DictionaryRepository dictionaryRepository;

    public Cards_wordsServiceImpl(Cards_wordsRepository cardsWordsRepository,
                                  UserRepository userRepository,
                                  WordRepository wordRepository,
                                  DictionaryRepository dictionaryRepository) {
        this.cardsWordsRepository = cardsWordsRepository;
        this.userRepository = userRepository;
        this.wordRepository = wordRepository;
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public Cards_words getCards_words(int cards_wordsId) {
        return cardsWordsRepository.getByIdCardWords(cards_wordsId).orElse(null);
    }

    @Override
    @Transactional
    public void createCards_words(int user_id, int word_id, int dictionary_id,
                                  int study_lvl, LocalDate next_review) {

        Optional<User> user = userRepository.findById(user_id);
        Optional<Word> word = wordRepository.findById(word_id);
        Optional<Dictionary> dictionary = dictionaryRepository.findById(dictionary_id);

        Cards_words card = new Cards_words(user.get(), word.get(), dictionary.get(), study_lvl, next_review);
        cardsWordsRepository.save(card);
    }
}
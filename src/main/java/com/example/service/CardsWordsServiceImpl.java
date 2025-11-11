package com.example.service;

import com.example.model.*;
import com.example.dto.CardsWordsDto;
import com.example.mapper.CardsWordsMapper;
import com.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CardsWordsServiceImpl implements CardsWordsService {
    private final CardsWordsRepository cardsWordsRepository;
    private final UserRepository userRepository;
    private final WordRepository wordRepository;
    private final DictionaryRepository dictionaryRepository;
    private final CardsWordsMapper cardsWordsMapper;

    public CardsWordsServiceImpl(CardsWordsRepository cardsWordsRepository,
                                 UserRepository userRepository,
                                 WordRepository wordRepository,
                                 DictionaryRepository dictionaryRepository,
                                 CardsWordsMapper cardsWordsMapper) { 
        this.cardsWordsRepository = cardsWordsRepository;
        this.userRepository = userRepository;
        this.wordRepository = wordRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.cardsWordsMapper = cardsWordsMapper;
    }

    @Override
    public CardsWordsDto getCardsWords(int cards_wordsId) { 
        CardsWords cardsWords = cardsWordsRepository.getByIdCardWords(cards_wordsId).orElse(null);
        return cardsWords != null ? cardsWordsMapper.toDto(cardsWords) : null;
    }

    @Override
    @Transactional
    public void createCardsWords(int user_id,
                                 int word_id,
                                 int dictionary_id,
                                 int study_lvl,
                                 LocalDate next_review) {

        Optional<User> user = userRepository.findById(user_id);
        Optional<Word> word = wordRepository.findById(word_id);
        Optional<Dictionary> dictionary = dictionaryRepository.findById(dictionary_id);

        CardsWords card = new CardsWords(user.get(), word.get(), dictionary.get(), study_lvl, next_review);
        cardsWordsRepository.save(card);
    }
    
    @Transactional
    public CardsWordsDto createCardsWords(CardsWordsDto cardsWordsDto) {
        Optional<User> user = userRepository.findById(cardsWordsDto.getUserId());
        Optional<Word> word = wordRepository.findById(cardsWordsDto.getWordId());
        Optional<Dictionary> dictionary = dictionaryRepository.findById(cardsWordsDto.getDictionaryId());

        if (user.isPresent() && word.isPresent() && dictionary.isPresent()) {
            CardsWords card = new CardsWords(
                user.get(), 
                word.get(), 
                dictionary.get(), 
                cardsWordsDto.getStudyLevel(), 
                cardsWordsDto.getNextReview()
            );
            CardsWords savedCard = cardsWordsRepository.save(card);
            return cardsWordsMapper.toDto(savedCard);
        }
        return null;
    }
}
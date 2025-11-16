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
    public CardsWordsDto getCardsWords(int cardsWordsId) {
        CardsWords cardsWords = cardsWordsRepository.getByIdCardWords(cardsWordsId).orElse(null);
        return cardsWords != null ? cardsWordsMapper.toDto(cardsWords) : null;
    }

    @Override
    @Transactional
    public void createCardsWords(int userId,
                                 int wordId,
                                 int dictionaryId,
                                 int studyLvl,
                                 LocalDate nextReview) {

        Optional<User> user = userRepository.findById(userId);
        Optional<Word> word = wordRepository.findById(wordId);
        Optional<Dictionary> dictionary = dictionaryRepository.findById(dictionaryId);

        CardsWords card = new CardsWords(user.get(), word.get(), dictionary.get(), studyLvl, nextReview);
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
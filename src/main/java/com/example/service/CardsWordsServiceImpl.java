package com.example.service;

import com.example.model.*;
import com.example.dto.CardsWordsDto;
import com.example.mapper.CardsWordsMapper;
import com.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
                                 LocalDateTime nextReview) {

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

    @Override
    @Transactional
    public void updateWordStatus(int userId, List<CardsWordsDto> updates){
        for(CardsWordsDto dto : updates){
            CardsWords card = cardsWordsRepository.findByUserIdAndWordId(userId,dto.getWordId()).orElseThrow();

            int newLvl = dto.getStudyLevel();
            card.setStudyLevel(newLvl);

            LocalDateTime next;
            switch (newLvl){
                case 1 -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusMinutes(5);
                case 2 -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusHours(1);
                case 3 -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(1);
                case 4 -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusWeeks(1);
                case 5 -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusMonths(1);
                case 6 -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusMonths(3);
                default -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            }
            card.setNextReview(next);
            cardsWordsRepository.save(card);
        }
    }
}
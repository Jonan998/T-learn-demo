package com.example.service;

import com.example.dto.WordDto;
import com.example.model.CardsWords;
import com.example.model.Dictionary;
import com.example.model.User;
import com.example.model.Word;
import com.example.repository.CardsWordsRepository;
import com.example.repository.DictionaryRepository;
import com.example.repository.UserRepository;
import com.example.repository.WordRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeckServiceImpl implements DeckService {

    private final UserRepository userRepository;
    private final WordRepository wordRepository;
    private final CardsWordsRepository cardsWordsRepository;
    private final DictionaryRepository dictionaryRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public DeckServiceImpl(UserRepository userRepository,
                           WordRepository wordRepository,
                           CardsWordsRepository cardsWordsRepository,
                           DictionaryRepository dictionaryRepository,
                           RedisTemplate<String, Object> redisTemplate) {
        this.userRepository = userRepository;
        this.wordRepository = wordRepository;
        this.cardsWordsRepository = cardsWordsRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<WordDto> getNewDeck(int userId) {

        String key = "user:" + userId + ":deck_new";

        List<WordDto> cached = (List<WordDto>) redisTemplate.opsForValue().get(key);
        if (cached != null && !cached.isEmpty()) {
            return cached;
        }

        User user = userRepository.findById(userId).orElseThrow();
        int limit = user.getLimitNew();

        List<WordDto> deck = wordRepository.getNewDeckWords(userId, limit);

        List<CardsWords> cards = new ArrayList<>();

        for (WordDto dto : deck) {

            Word word = wordRepository.findById(dto.getId())
                    .orElseThrow();

            Integer dictionaryId = wordRepository.findDictionaryId(dto.getId());

            Dictionary dict = dictionaryRepository.findById(dictionaryId)
                    .orElseThrow();

            cards.add(
                new CardsWords(user, word, dict, 1, LocalDate.now().minusWeeks(2))
            );
        }

        cardsWordsRepository.saveAll(cards);
        redisTemplate.opsForValue().set(key, deck, Duration.ofHours(12));

        return deck;
    }

    @Override
    public List<WordDto> getRepeatDeck(int userId) {

        String key = "user:" + userId + ":deck_repeat";

        List<WordDto> cached = (List<WordDto>) redisTemplate.opsForValue().get(key);
        if (cached != null && !cached.isEmpty()) {
            return cached;
        }

        User user = userRepository.findById(userId).orElseThrow();
        int limit = user.getLimitRepeat();

        List<WordDto> deck = cardsWordsRepository.getRepeatDeckWords(userId, limit);

        redisTemplate.opsForValue().set(key, deck, Duration.ofHours(12));

        return deck;
    }
}
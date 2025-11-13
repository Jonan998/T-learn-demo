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
public class DeckService {
    private final RedisTemplate<String,Object> redisTemplate;
    private final CardsWordsRepository cardsWordsRepository;
    private final UserRepository userRepository;
    private final WordRepository wordRepository;
    private final DictionaryRepository dictionaryRepository;

    public DeckService(RedisTemplate<String, Object> redisTemplate,
                       CardsWordsRepository cardsWordsRepository,
                       WordRepository wordRepository,
                       UserRepository userRepository,
                       DictionaryRepository dictionaryRepository) {
        this.redisTemplate = redisTemplate;
        this.cardsWordsRepository = cardsWordsRepository;
        this.wordRepository = wordRepository;
        this.userRepository = userRepository;
        this.dictionaryRepository = dictionaryRepository;
    }

    public List<WordDto> getNewDeck(int user_id){
        String key = "user:" + user_id + ":deck_new";

        List<WordDto> cached = (List<WordDto>) redisTemplate.opsForValue().get(key);
        if (cached != null && !cached.isEmpty()) {
            return cached;
        }

        User user = userRepository.findById(user_id).orElseThrow();
        int limit = user.getLimit_new();

        List<Object[]> rows = wordRepository.getNewDeckWords(user_id,limit);

        List<CardsWords> cards = new ArrayList<>();
        List<WordDto> deck = new ArrayList<>();

        for (Object[] row : rows) {
            Integer wordId = ((Number) row[0]).intValue();
            String eng = (String) row[1];
            String rus = (String) row[2];
            String transcription = (String) row[3];
            Integer dictionaryId = ((Number) row[4]).intValue();

            Word word = wordRepository.findById(wordId)
                    .orElseThrow(() -> new RuntimeException("Word not found: " + wordId));

            Dictionary dictionary = dictionaryRepository.findById(dictionaryId)
                    .orElseThrow(() -> new RuntimeException("Dictionary not found: " + dictionaryId));

            cards.add(new CardsWords(user, word, dictionary, 0, LocalDate.now()));
            deck.add(new WordDto(wordId, eng, rus, transcription));
        }


        cardsWordsRepository.saveAll(cards);
        redisTemplate.opsForValue().set(key, deck, Duration.ofHours(12));

        return deck;
    }

}
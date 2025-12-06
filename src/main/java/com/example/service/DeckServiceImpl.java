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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DeckServiceImpl implements DeckService {

    private final UserRepository userRepository;
    private final WordRepository wordRepository;
    private final CardsWordsRepository cardsWordsRepository;
    private final DictionaryRepository dictionaryRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JdbcTemplate jdbc;


    public DeckServiceImpl(UserRepository userRepository,
                           WordRepository wordRepository,
                           CardsWordsRepository cardsWordsRepository,
                           DictionaryRepository dictionaryRepository,
                           RedisTemplate<String, Object> redisTemplate,
                           JdbcTemplate jdbc
    ) {
        this.userRepository = userRepository;
        this.wordRepository = wordRepository;
        this.cardsWordsRepository = cardsWordsRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.redisTemplate = redisTemplate;
        this.jdbc = jdbc;
    }

    @Override
    public List<WordDto> getNewDeck(int userId) {

        log.info("Запрос новой колоды для userId={}", userId);

        String key = "user:" + userId + ":deck_new";

        List<WordDto> cached = (List<WordDto>) redisTemplate.opsForValue().get(key);
        if (cached != null && !cached.isEmpty()) {
            log.info("Колода найдена в Redis по ключу {}", key);
            return cached;
        }

        log.info("Колода в Redis не найдена. Загружаем из БД.");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Пользователь {} не найден", userId);
                    return new RuntimeException("Пользователь не найден");
                });

        int limit = user.getLimitNew();
        log.debug("Предел новых слов: {}", limit);

        List<WordDto> deck = wordRepository.getNewDeckWords(userId, limit);
        log.info("Получено {} слов из БД", deck.size());

        List<CardsWords> cards = new ArrayList<>();

        for (WordDto dto : deck) {
            log.debug("Обрабатываем слово id={}", dto.getId());

            Word word = wordRepository.findById(dto.getId())
                    .orElseThrow(() -> {
                        log.error("Слово {} не найдено", dto.getId());
                        return new RuntimeException("Слово не найдено");
                    });

            Integer dictionaryId = wordRepository.findDictionaryId(dto.getId());

            Dictionary dict = dictionaryRepository.findById(dictionaryId)
                    .orElseThrow();

            cards.add(
                new CardsWords(user, word, dict, 0, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            );
        }

        cardsWordsRepository.saveAll(cards);
        log.info("Сохранено {} карточек в cards_words", cards.size());

        redisTemplate.opsForValue().set(key, deck, Duration.ofHours(12));
        log.info("Колода сохранена в Redis на 12 часов");

        return deck;
    }

    @Override
    public List<WordDto> getRepeatDeck(int userId) {
        log.info("Запрос колоды повторения для userId={}", userId);

        String key = "user:" + userId + ":deck_repeat";

        List<WordDto> cached = (List<WordDto>) redisTemplate.opsForValue().get(key);
        if (cached != null && !cached.isEmpty()) {
            log.info("Колода повторения найдена в Redis по ключу {}", key);
            return cached;
        }

        log.info("Колода повторения не найдена в Redis. Загружаем из БД.");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Пользователь {} не найден", userId);
                    return new RuntimeException("Пользователь не найден");
                });

        int limit = user.getLimitRepeat();
        log.debug("Предел новых слов: {}", limit);

        List<WordDto> deck = cardsWordsRepository.getRepeatDeckWords(userId, limit);

        redisTemplate.opsForValue().set(key, deck, Duration.ofHours(12));
        log.info("Колода повторения сохранена в Redis на 12 часов");

        return deck;
    }


}
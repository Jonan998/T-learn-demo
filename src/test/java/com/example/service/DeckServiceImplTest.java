package com.example.service;

import com.example.dto.DictionaryDto;
import com.example.dto.WordDto;
import com.example.model.CardsWords;
import com.example.model.Dictionary;
import com.example.model.User;
import com.example.model.Word;
import com.example.repository.CardsWordsRepository;
import com.example.repository.DictionaryRepository;
import com.example.repository.UserRepository;
import com.example.repository.WordRepository;
import com.example.service.DeckServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeckServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WordRepository wordRepository;

    @Mock
    private CardsWordsRepository cardsWordsRepository;

    @Mock
    private DictionaryRepository dictionaryRepository;

    @Mock
    private JdbcTemplate jdbc;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    private DeckService deckService;

    private DictionaryService dictionaryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        this.deckService = new DeckServiceImpl(
                userRepository,
                wordRepository,
                cardsWordsRepository,
                dictionaryRepository,
                redisTemplate,
                jdbc
        );

        this.dictionaryService = new DictionaryServiceImpl(dictionaryRepository,null,null);
    }


    @Test
    void testGetNewDeck_WithEmptyCache() {
        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setLimitNew(2);

        WordDto wordDto1 = new WordDto();
        wordDto1.setId(101);
        WordDto wordDto2 = new WordDto();
        wordDto2.setId(102);

        Word word1 = new Word();
        word1.setId(101);
        Word word2 = new Word();
        word2.setId(102);

        Dictionary dict1 = new Dictionary();
        dict1.setId(201);
        Dictionary dict2 = new Dictionary();
        dict2.setId(202);

        when(valueOperations.get("user:1:deck_new")).thenReturn(null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(wordRepository.getNewDeckWords(userId, 2)).thenReturn(Arrays.asList(wordDto1, wordDto2));
        when(wordRepository.findById(101)).thenReturn(Optional.of(word1));
        when(wordRepository.findById(102)).thenReturn(Optional.of(word2));
        when(wordRepository.findDictionaryId(101)).thenReturn(201);
        when(wordRepository.findDictionaryId(102)).thenReturn(202);
        when(dictionaryRepository.findById(201)).thenReturn(Optional.of(dict1));
        when(dictionaryRepository.findById(202)).thenReturn(Optional.of(dict2));
        when(cardsWordsRepository.saveAll(anyList())).thenReturn(null);

        List<WordDto> result = deckService.getNewDeck(userId);

        assertEquals(2, result.size());
        verify(valueOperations, times(1)).set(eq("user:1:deck_new"), anyList(), any());
        verify(cardsWordsRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testGetRepeatDeck_WithCache() {
        int userId = 1;
        WordDto wordDto1 = new WordDto();
        WordDto wordDto2 = new WordDto();
        List<WordDto> cachedList = Arrays.asList(wordDto1, wordDto2);

        when(valueOperations.get("user:1:deck_repeat")).thenReturn(cachedList);

        List<WordDto> result = deckService.getRepeatDeck(userId);

        assertEquals(2, result.size());
        verify(userRepository, never()).findById(anyInt());
        verify(cardsWordsRepository, never()).getRepeatDeckWords(anyInt(), anyInt());
        verify(valueOperations, never()).set(anyString(), anyList(), any());
    }

    @Test
    void testGetUserDictionaries(){

        List<DictionaryDto> dict = List.of(
                new DictionaryDto(5,"animals","en-ru"),
                new DictionaryDto(6,"food","en-ru"),
                new DictionaryDto(7,"colors","en-ru")
        );

        when(dictionaryRepository.findUserDictionaries(3)).thenReturn(dict);

        List<DictionaryDto> result = dictionaryService.getUserDictionaries(3);

        assertEquals(3, result.size());

        verify(dictionaryRepository,times(1)).findUserDictionaries(3);
    }
}

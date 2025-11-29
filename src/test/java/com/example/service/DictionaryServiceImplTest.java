package com.example.service;

import com.example.dto.WordDto;
import com.example.mapper.DictionaryMapper;
import com.example.repository.DictionaryRepository;
import com.example.repository.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DictionaryServiceImplTest {

    @Mock
    private WordRepository wordRepository;

    @Mock
    private DictionaryRepository dictionaryRepository;

    @InjectMocks
    private DictionaryServiceImpl dictionaryService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWordsByDictionaryId() {

        List<WordDto> words = List.of(
                new WordDto(5, "one", "один", "/wʌn/"),
                new WordDto(6, "two", "два", "/tuː/")
        );

        when(wordRepository.findWordsByDictionaryId(99)).thenReturn(words);

        List<WordDto> result = dictionaryService.getWordsByDictionaryId(99);

        assertEquals(2, result.size());
        assertEquals("one", result.get(0).getEngLang());
        verify(wordRepository, times(1)).findWordsByDictionaryId(99);
    }
}




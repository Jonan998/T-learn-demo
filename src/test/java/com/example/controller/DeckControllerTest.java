package com.example.controller;

import com.example.dto.CardsWordsDto;
import com.example.dto.DictionaryDto;
import com.example.dto.WordDto;
import com.example.service.AuthService;
import com.example.service.CardsWordsServiceImpl;
import com.example.service.DeckServiceImpl;
import com.example.service.DictionaryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeckServiceImpl deckService;

    @MockBean
    private DictionaryServiceImpl dictionaryService;

    @MockBean
    private CardsWordsServiceImpl cardsWordsService;

    @MockBean
    private AuthService authService;

    @MockBean
    private HttpServletRequest request;

    @Test
    void testGetUserDictionaries() throws Exception {
        List<DictionaryDto> dictionaries = List.of(
                new DictionaryDto(1, "Basic", "EN"),
                new DictionaryDto(2, "Travel", "EN")
        );

        when(dictionaryService.getUserDictionaries(1)).thenReturn(dictionaries);

        mockMvc.perform(get("/learning/dictionary")
                        .param("user_id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Basic"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Travel"));
    }

    @Test
    void testGetNewDeck() throws Exception {
        List<WordDto> words = List.of(
                new WordDto(1, 0, "dog", "собака", "trans:dog"),
                new WordDto(2, 0, "cat", "кошка", "trans:cat"),
                new WordDto(3, 0, "fish", "рыба", "trans:fish")
        );

        when(authService.getUserId(any(HttpServletRequest.class))).thenReturn(1);
        when(deckService.getNewDeck(1)).thenReturn(words);

        mockMvc.perform(get("/learning/words/new"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].studyLvl").value(0))
                .andExpect(jsonPath("$[0].engLang").value("dog"))
                .andExpect(jsonPath("$[0].rusLang").value("собака"))
                .andExpect(jsonPath("$[0].transcription").value("trans:dog"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].studyLvl").value(0))
                .andExpect(jsonPath("$[1].engLang").value("cat"))
                .andExpect(jsonPath("$[1].rusLang").value("кошка"))
                .andExpect(jsonPath("$[1].transcription").value("trans:cat"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].studyLvl").value(0))
                .andExpect(jsonPath("$[2].engLang").value("fish"))
                .andExpect(jsonPath("$[2].rusLang").value("рыба"))
                .andExpect(jsonPath("$[2].transcription").value("trans:fish"));
    }

    @Test
    void testGetRepeatDeck() throws Exception {
        List<WordDto> words = List.of(
                new WordDto(1, 4, "dog", "собака", "trans:dog"),
                new WordDto(2, 5, "cat", "кошка", "trans:cat"),
                new WordDto(3, 1, "fish", "рыба", "trans:fish")
        );

        when(deckService.getRepeatDeck(4)).thenReturn(words);

        mockMvc.perform(get("/learning/words/repeat")
                        .param("user_id", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].studyLvl").value(4))
                .andExpect(jsonPath("$[0].engLang").value("dog"))
                .andExpect(jsonPath("$[0].rusLang").value("собака"))
                .andExpect(jsonPath("$[0].transcription").value("trans:dog"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].studyLvl").value(5))
                .andExpect(jsonPath("$[1].engLang").value("cat"))
                .andExpect(jsonPath("$[1].rusLang").value("кошка"))
                .andExpect(jsonPath("$[1].transcription").value("trans:cat"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].studyLvl").value(1))
                .andExpect(jsonPath("$[2].engLang").value("fish"))
                .andExpect(jsonPath("$[2].rusLang").value("рыба"))
                .andExpect(jsonPath("$[2].transcription").value("trans:fish"));
    }
}

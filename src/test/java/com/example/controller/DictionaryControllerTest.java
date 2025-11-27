package com.example.controller;

import com.example.dto.WordDto;
import com.example.service.DictionaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DictionaryController.class)
@AutoConfigureMockMvc(addFilters = false)
class DictionaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DictionaryService dictionaryService;

    @MockBean
    private com.example.Security.JwtUtil jwtUtil;

    @MockBean
    private com.example.Security.CustomUserDetailsService userDetailsService;

    @Test
    void testGetWordsByDictionaryId() throws Exception {

        List<WordDto> words = List.of(
                new WordDto(10, "cat", "кошка", "/kæt/"),
                new WordDto(11, "dog", "собака", "/dɔg/")
        );

        when(dictionaryService.getWordsByDictionaryId(13)).thenReturn(words);

        mockMvc.perform(get("/dictionary")
                        .param("dictionary_id", "13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].engLang").value("cat"))
                .andExpect(jsonPath("$[0].rusLang").value("кошка"))
                .andExpect(jsonPath("$[0].transcription").value("/kæt/"))
                .andExpect(jsonPath("$[0].studyLvl").doesNotExist())
                .andExpect(jsonPath("$[0].priority").doesNotExist());
    }

}

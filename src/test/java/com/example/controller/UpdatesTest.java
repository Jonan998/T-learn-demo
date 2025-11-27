package com.example.controller;

import com.example.dto.CardsWordsDto;
import com.example.dto.UserDto;
import com.example.service.CardsWordsService;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UpdatesTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CardsWordsService cardsWordsService;

    @MockBean
    UserService userService;

    @Test
    void testUpdateWordStatus() throws Exception {
        List<CardsWordsDto> updates = List.of(
                new CardsWordsDto(1, 3),
                new CardsWordsDto(2, 2)
        );

        doNothing().when(cardsWordsService).updateWordStatus(1, updates);

        mockMvc.perform(
                patch("/learning/progress")
                        .param("user_id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updates))
        ).andExpect(status().isOk());

        verify(cardsWordsService).updateWordStatus(1, updates);
    }

    @Test
    void testUpdateUserSettings() throws Exception {
        UserDto dto = new UserDto();
        dto.setName("NewName");
        dto.setLimitNew(20);
        dto.setLimitRepeat(30);

        doNothing().when(userService).updateUserSettings(1, dto);

        mockMvc.perform(
                patch("/user/settings")
                        .param("user_id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto))
        ).andExpect(status().isOk());

        verify(userService).updateUserSettings(1, dto);
    }
}

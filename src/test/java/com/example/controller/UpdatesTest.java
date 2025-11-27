package com.example.controller;

import com.example.dto.CardsWordsDto;
import com.example.dto.UserDto;
import com.example.model.CardsWords;
import com.example.model.Dictionary;
import com.example.model.User;
import com.example.model.Word;
import com.example.repository.CardsWordsRepository;
import com.example.repository.DictionaryRepository;
import com.example.repository.UserRepository;
import com.example.repository.WordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@ActiveProfiles("test")
class UpdatesTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    CardsWordsRepository cardsWordsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WordRepository wordRepository;

    @Autowired
    DictionaryRepository dictionaryRepository;

    @MockBean
    private com.example.Security.JwtUtil jwtUtil;

    @MockBean
    private com.example.Security.CustomUserDetailsService userDetailsService;

    @Test
    void testUpdateWordStatus() throws Exception {

        User user = userRepository.save(
                new User("TestUser", "pass",
                        LocalDate.now(), LocalDate.now(), 10, 10)
        );

        Word w1 = wordRepository.save(new Word("w1", "r1"));
        Word w2 = wordRepository.save(new Word("w2", "r2"));
        Dictionary dict = dictionaryRepository.save(new Dictionary("default"));

        cardsWordsRepository.save(new CardsWords(user, w1, dict, 1, LocalDateTime.now()));
        cardsWordsRepository.save(new CardsWords(user, w2, dict, 1, LocalDateTime.now()));

        List<CardsWordsDto> updates = List.of(
                new CardsWordsDto(w1.getId(), 3),
                new CardsWordsDto(w2.getId(), 2)
        );

        mockMvc.perform(
                patch("/learning/progress")
                        .param("user_id", String.valueOf(user.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updates))
        ).andExpect(status().isOk());

        CardsWords upd1 =
                cardsWordsRepository.findByUserIdAndWordId(user.getId(), w1.getId()).orElseThrow();
        CardsWords upd2 =
                cardsWordsRepository.findByUserIdAndWordId(user.getId(), w2.getId()).orElseThrow();

        assert upd1.getStudyLevel() == 3;
        assert upd2.getStudyLevel() == 2;

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        assert upd1.getNextReview().isAfter(now.plusHours(23));
        assert upd1.getNextReview().isBefore(now.plusHours(25));

        assert upd2.getNextReview().isAfter(now.plusMinutes(50));
        assert upd2.getNextReview().isBefore(now.plusMinutes(70));
    }

    @Test
    void testUpdateUserSettings() throws Exception {

        User user = userRepository.save(
                new User("OldName", "pass",
                        LocalDate.now(), LocalDate.now(), 5, 5)
        );

        int userId = user.getId();

        UserDto dto = new UserDto();
        dto.setName("NewName");
        dto.setLimitNew(20);
        dto.setLimitRepeat(30);

        mockMvc.perform(
                patch("/user/settings")
                        .param("user_id", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto))
        ).andExpect(status().isOk());

        User updated = userRepository.findById(userId).orElseThrow();

        assert updated.getName().equals("NewName");
        assert updated.getLimitNew() == 20;
        assert updated.getLimitRepeat() == 30;
    }

}


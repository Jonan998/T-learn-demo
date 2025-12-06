package com.example.service;

import com.example.dto.CardsWordsDto;
import com.example.model.CardsWords;
import com.example.repository.CardsWordsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardsWordsServiceImplTest {

    @Mock
    private CardsWordsRepository cardsWordsRepository;

    private CardsWordsService cardsWordsService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        cardsWordsService = new CardsWordsServiceImpl(cardsWordsRepository,null,null,null,null);
    }

    @Test
    void testUpdateWordStatus_Level3() {

        int userId = 5;

        CardsWordsDto dto = new CardsWordsDto();
        dto.setWordId(10);
        dto.setStudyLevel(3);

        List<CardsWordsDto> updates = List.of(dto);

        CardsWords existingCard = new CardsWords();
        existingCard.setStudyLevel(1);

        when(cardsWordsRepository.findByUserIdAndWordId(userId, 10))
                .thenReturn(Optional.of(existingCard));

        cardsWordsService.updateWordStatus(userId, updates);

        assertEquals(3, existingCard.getStudyLevel());

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime expected = now.plusDays(1);

        long diff = Math.abs(ChronoUnit.SECONDS.between(expected, existingCard.getNextReview()));
        assertTrue(diff < 2, "nextReview should be approx +1 day");

        verify(cardsWordsRepository, times(1)).save(existingCard);
    }
}

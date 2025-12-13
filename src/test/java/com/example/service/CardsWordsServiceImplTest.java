package com.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.dto.CardsWordsDto;
import com.example.model.CardsWords;
import com.example.repository.CardsWordsRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class CardsWordsServiceImplTest {

  @Mock private CardsWordsRepository cardsWordsRepository;

  private CardsWordsService cardsWordsService;

  @Mock private RateLimitService rateLimitService;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
    when(rateLimitService.isRateLimitExceeded(anyInt(), anyInt(), any(Duration.class)))
        .thenReturn(false);

    SecurityContext context = SecurityContextHolder.createEmptyContext();

    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken("testUser", null, List.of());

    context.setAuthentication(auth);
    SecurityContextHolder.setContext(context);
    cardsWordsService =
        new CardsWordsServiceImpl(cardsWordsRepository, null, null, null, null, rateLimitService);
  }

  @AfterEach
  void clearSecurityContext() {
    SecurityContextHolder.clearContext();
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

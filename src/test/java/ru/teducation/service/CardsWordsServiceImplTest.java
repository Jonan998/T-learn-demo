package ru.teducation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import ru.teducation.dto.CardsWordsDto;
import ru.teducation.model.CardsWords;
import ru.teducation.repository.CardsWordsRepository;

class CardsWordsServiceImplTest {

  @Mock private CardsWordsRepository cardsWordsRepository;

  private CardsWordsService cardsWordsService;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);

    SecurityContext context = SecurityContextHolder.createEmptyContext();

    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken("testUser", null, List.of());

    context.setAuthentication(auth);
    SecurityContextHolder.setContext(context);
    cardsWordsService = new CardsWordsServiceImpl(cardsWordsRepository, null, null, null, null);
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

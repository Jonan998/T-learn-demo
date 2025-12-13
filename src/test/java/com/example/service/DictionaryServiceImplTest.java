package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.dto.WordDto;
import com.example.repository.DictionaryRepository;
import com.example.repository.WordRepository;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class DictionaryServiceImplTest {

  @Mock private WordRepository wordRepository;

  @Mock private DictionaryRepository dictionaryRepository;

  private DictionaryService dictionaryService;

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

    dictionaryService =
        new DictionaryServiceImpl(dictionaryRepository, null, wordRepository, rateLimitService);
  }

  @AfterEach
  void clearSecurityContext() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void testGetWordsByDictionaryId() {

    List<WordDto> words =
        List.of(new WordDto(5, "one", "один", "/wʌn/"), new WordDto(6, "two", "два", "/tuː/"));

    when(wordRepository.findWordsByDictionaryId(99)).thenReturn(words);

    List<WordDto> result = dictionaryService.getWordsByDictionaryId(99);

    assertEquals(2, result.size());
    assertEquals("one", result.get(0).getEngLang());
    verify(wordRepository, times(1)).findWordsByDictionaryId(99);
  }
}

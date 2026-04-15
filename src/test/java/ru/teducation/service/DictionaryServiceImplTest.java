package ru.teducation.service;

import static org.mockito.Mockito.*;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.teducation.repository.DictionaryRepository;
import ru.teducation.repository.WordRepository;

class DictionaryServiceImplTest {

  @Mock private WordRepository wordRepository;

  @Mock private DictionaryRepository dictionaryRepository;

  private DictionaryService dictionaryService;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);

    SecurityContext context = SecurityContextHolder.createEmptyContext();

    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken("testUser", null, List.of());

    context.setAuthentication(auth);
    SecurityContextHolder.setContext(context);
    dictionaryService =
        new DictionaryServiceImpl(dictionaryRepository, null, wordRepository, null, null, null);
  }

  @AfterEach
  void clearSecurityContext() {
    SecurityContextHolder.clearContext();
  }
}

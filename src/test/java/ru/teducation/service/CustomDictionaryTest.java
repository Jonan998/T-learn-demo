package ru.teducation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.teducation.dto.DictionaryWordsDto;
import ru.teducation.dto.WordDto;
import ru.teducation.exception.ConflictException;
import ru.teducation.exception.NotFoundException;
import ru.teducation.mapper.WordMapper;
import ru.teducation.model.Dictionary;
import ru.teducation.model.DictionaryWords;
import ru.teducation.model.User;
import ru.teducation.model.Word;
import ru.teducation.repository.DictionaryRepository;
import ru.teducation.repository.DictionaryWordsRepository;
import ru.teducation.repository.WordRepository;

@ExtendWith(MockitoExtension.class)
class CustomDictionaryTest {

  @Mock private WordRepository wordRepository;
  @Mock private DictionaryRepository dictionaryRepository;
  @Mock private DictionaryWordsRepository dictionaryWordsRepository;
  @Mock private WordMapper wordMapper;

  @InjectMocks private DictionaryServiceImpl service;

  private User owner;
  private Dictionary dictionary;

  @BeforeEach
  void setUp() {
    owner = new User();
    owner.setId(10);

    dictionary = new Dictionary();
    dictionary.setId(100);
    dictionary.setOwner(owner);
  }

  @Test
  void searchWord_returnsMappedDtos() {
    String prefix = "ab";
    List<Word> words = List.of(new Word(), new Word());
    List<WordDto> dtos = List.of(new WordDto(), new WordDto());

    when(wordRepository.findTop10ByEngLangStartingWithIgnoreCase(prefix)).thenReturn(words);
    when(wordMapper.toDtoList(words)).thenReturn(dtos);

    List<WordDto> result = service.searchWord(prefix);

    assertSame(dtos, result);
    verify(wordRepository).findTop10ByEngLangStartingWithIgnoreCase(prefix);
    verify(wordMapper).toDtoList(words);
    verifyNoMoreInteractions(wordRepository, wordMapper);
  }

  @Test
  void addNewWord_success_savesRelation() {
    int userId = 10;
    int wordId = 1;
    int dictionaryId = 100;

    DictionaryWordsDto dto = new DictionaryWordsDto();
    dto.setWordId(wordId);
    dto.setDictionaryId(dictionaryId);

    Word word = new Word();
    word.setId(wordId);

    when(wordRepository.findById(wordId)).thenReturn(Optional.of(word));
    when(dictionaryRepository.findById(dictionaryId)).thenReturn(Optional.of(dictionary));
    when(dictionaryWordsRepository.existsByDictionaryIdAndWordId(dictionaryId, wordId))
        .thenReturn(false);

    service.addNewWord(userId, dto);

    verify(wordRepository).findById(wordId);
    verify(dictionaryRepository).findById(dictionaryId);
    verify(dictionaryWordsRepository).existsByDictionaryIdAndWordId(dictionaryId, wordId);

    verify(dictionaryWordsRepository)
        .save(
            argThat(
                rel -> rel != null && rel.getWord() == word && rel.getDictionary() == dictionary));

    verifyNoMoreInteractions(wordRepository, dictionaryRepository, dictionaryWordsRepository);
  }

  @Test
  void addNewWord_wordNotFound_throwsNotFound() {
    int userId = 10;
    int wordId = 1;
    int dictionaryId = 100;

    DictionaryWordsDto dto = new DictionaryWordsDto();
    dto.setWordId(wordId);
    dto.setDictionaryId(dictionaryId);

    when(wordRepository.findById(wordId)).thenReturn(Optional.empty());

    NotFoundException ex =
        assertThrows(NotFoundException.class, () -> service.addNewWord(userId, dto));
    assertEquals("Слово не найдено", ex.getMessage());

    verify(wordRepository).findById(wordId);
    verifyNoMoreInteractions(wordRepository, dictionaryRepository, dictionaryWordsRepository);
  }

  @Test
  void addNewWord_dictionaryNotFound_throwsNotFound() {
    int userId = 10;
    int wordId = 1;
    int dictionaryId = 100;

    DictionaryWordsDto dto = new DictionaryWordsDto();
    dto.setWordId(wordId);
    dto.setDictionaryId(dictionaryId);

    when(wordRepository.findById(wordId)).thenReturn(Optional.of(new Word()));
    when(dictionaryRepository.findById(dictionaryId)).thenReturn(Optional.empty());

    NotFoundException ex =
        assertThrows(NotFoundException.class, () -> service.addNewWord(userId, dto));
    assertEquals("Словарь не найден", ex.getMessage());

    verify(wordRepository).findById(wordId);
    verify(dictionaryRepository).findById(dictionaryId);
    verifyNoMoreInteractions(wordRepository, dictionaryRepository, dictionaryWordsRepository);
  }

  @Test
  void addNewWord_noRights_throwsConflict() {
    int userId = 999;
    int wordId = 1;
    int dictionaryId = 100;

    DictionaryWordsDto dto = new DictionaryWordsDto();
    dto.setWordId(wordId);
    dto.setDictionaryId(dictionaryId);

    when(wordRepository.findById(wordId)).thenReturn(Optional.of(new Word()));
    when(dictionaryRepository.findById(dictionaryId)).thenReturn(Optional.of(dictionary));

    ConflictException ex =
        assertThrows(ConflictException.class, () -> service.addNewWord(userId, dto));
    assertEquals("Нет прав", ex.getMessage());

    verify(wordRepository).findById(wordId);
    verify(dictionaryRepository).findById(dictionaryId);
    verifyNoMoreInteractions(wordRepository, dictionaryRepository, dictionaryWordsRepository);
  }

  @Test
  void addNewWord_alreadyExists_throwsConflict() {
    int userId = 10;
    int wordId = 1;
    int dictionaryId = 100;

    DictionaryWordsDto dto = new DictionaryWordsDto();
    dto.setWordId(wordId);
    dto.setDictionaryId(dictionaryId);

    when(wordRepository.findById(wordId)).thenReturn(Optional.of(new Word()));
    when(dictionaryRepository.findById(dictionaryId)).thenReturn(Optional.of(dictionary));
    when(dictionaryWordsRepository.existsByDictionaryIdAndWordId(dictionaryId, wordId))
        .thenReturn(true);

    ConflictException ex =
        assertThrows(ConflictException.class, () -> service.addNewWord(userId, dto));
    assertEquals("Такое слово уже есть в словаре", ex.getMessage());

    verify(wordRepository).findById(wordId);
    verify(dictionaryRepository).findById(dictionaryId);
    verify(dictionaryWordsRepository).existsByDictionaryIdAndWordId(dictionaryId, wordId);
    verify(dictionaryWordsRepository, never()).save(any());
    verifyNoMoreInteractions(wordRepository, dictionaryRepository, dictionaryWordsRepository);
  }

  @Test
  void deleteDictionary_success_deletes() {
    int userId = 10;
    int dictionaryId = 100;

    when(dictionaryRepository.findById(dictionaryId)).thenReturn(Optional.of(dictionary));

    service.deleteDictionary(userId, dictionaryId);

    verify(dictionaryRepository).findById(dictionaryId);
    verify(dictionaryRepository).delete(dictionary);
    verifyNoMoreInteractions(dictionaryRepository, wordRepository, dictionaryWordsRepository);
  }

  @Test
  void deleteDictionary_notFound_throwsNotFound() {
    int userId = 10;
    int dictionaryId = 100;

    when(dictionaryRepository.findById(dictionaryId)).thenReturn(Optional.empty());

    NotFoundException ex =
        assertThrows(NotFoundException.class, () -> service.deleteDictionary(userId, dictionaryId));
    assertEquals("Словарь не найден", ex.getMessage());

    verify(dictionaryRepository).findById(dictionaryId);
    verify(dictionaryRepository, never()).delete(any());
    verifyNoMoreInteractions(dictionaryRepository, wordRepository, dictionaryWordsRepository);
  }

  @Test
  void deleteDictionary_noRights_throwsConflict() {
    int userId = 999;
    int dictionaryId = 100;

    when(dictionaryRepository.findById(dictionaryId)).thenReturn(Optional.of(dictionary));

    ConflictException ex =
        assertThrows(ConflictException.class, () -> service.deleteDictionary(userId, dictionaryId));
    assertEquals("Нет прав", ex.getMessage());

    verify(dictionaryRepository).findById(dictionaryId);
    verify(dictionaryRepository, never()).delete(any());
    verifyNoMoreInteractions(dictionaryRepository, wordRepository, dictionaryWordsRepository);
  }

  @Test
  void removeWord_success_deletesRelation() {
    int userId = 10;
    int dictionaryId = 100;
    int wordId = 1;

    DictionaryWords relation = new DictionaryWords();
    relation.setDictionary(dictionary);
    relation.setWord(new Word());

    when(dictionaryRepository.findById(dictionaryId)).thenReturn(Optional.of(dictionary));
    when(dictionaryWordsRepository.findByDictionaryIdAndWordId(dictionaryId, wordId))
        .thenReturn(Optional.of(relation));

    service.removeWord(userId, dictionaryId, wordId);

    verify(dictionaryRepository).findById(dictionaryId);
    verify(dictionaryWordsRepository).findByDictionaryIdAndWordId(dictionaryId, wordId);
    verify(dictionaryWordsRepository).delete(relation);
    verifyNoMoreInteractions(dictionaryRepository, wordRepository, dictionaryWordsRepository);
  }

  @Test
  void removeWord_dictionaryNotFound_throwsNotFound() {
    int userId = 10;
    int dictionaryId = 100;
    int wordId = 1;

    when(dictionaryRepository.findById(dictionaryId)).thenReturn(Optional.empty());

    NotFoundException ex =
        assertThrows(
            NotFoundException.class, () -> service.removeWord(userId, dictionaryId, wordId));
    assertEquals("Словарь не найден", ex.getMessage());

    verify(dictionaryRepository).findById(dictionaryId);
    verifyNoMoreInteractions(dictionaryRepository, wordRepository, dictionaryWordsRepository);
  }

  @Test
  void removeWord_noRights_throwsConflict() {
    int userId = 999;
    int dictionaryId = 100;
    int wordId = 1;

    when(dictionaryRepository.findById(dictionaryId)).thenReturn(Optional.of(dictionary));

    ConflictException ex =
        assertThrows(
            ConflictException.class, () -> service.removeWord(userId, dictionaryId, wordId));
    assertEquals("Нет прав", ex.getMessage());

    verify(dictionaryRepository).findById(dictionaryId);
    verify(dictionaryWordsRepository, never()).findByDictionaryIdAndWordId(anyInt(), anyInt());
    verifyNoMoreInteractions(dictionaryRepository, wordRepository, dictionaryWordsRepository);
  }

  @Test
  void removeWord_relationNotFound_throwsNotFound() {
    int userId = 10;
    int dictionaryId = 100;
    int wordId = 1;

    when(dictionaryRepository.findById(dictionaryId)).thenReturn(Optional.of(dictionary));
    when(dictionaryWordsRepository.findByDictionaryIdAndWordId(dictionaryId, wordId))
        .thenReturn(Optional.empty());

    NotFoundException ex =
        assertThrows(
            NotFoundException.class, () -> service.removeWord(userId, dictionaryId, wordId));
    assertEquals("Слово не найдено в словаре", ex.getMessage());

    verify(dictionaryRepository).findById(dictionaryId);
    verify(dictionaryWordsRepository).findByDictionaryIdAndWordId(dictionaryId, wordId);
    verify(dictionaryWordsRepository, never()).delete(any());
    verifyNoMoreInteractions(dictionaryRepository, wordRepository, dictionaryWordsRepository);
  }
}

package ru.teducation.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.teducation.dto.WordDto;
import ru.teducation.model.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
  @Query(
      value =
          """
    SELECT * FROM words
    WHERE id IN (
            SELECT id FROM words
                    ORDER BY RANDOM()
    LIMIT :limit
    )
    ORDER BY RANDOM()""",
      nativeQuery = true)
  List<Word> getWords(@Param("limit") int count);

  @Query(
      value =
          """
    SELECT
        w.id AS id,
        0 AS study_lvl,            -- здесь ставим вторым поле study_lvl
        w.eng_lang AS engLang,
        w.rus_lang AS rusLang,
        w.transcription AS transcription
    FROM words w
    JOIN dictionary_words dw ON w.id = dw.word_id
    WHERE w.id NOT IN (
        SELECT word_id FROM cards_words WHERE user_id = :userId
    )
    ORDER BY RANDOM()
    LIMIT :limit
""",
      nativeQuery = true)
  List<WordDto> getNewDeckWords(@Param("userId") int userId, @Param("limit") int limit);

  @Query(
      value =
          """
    SELECT dw.dictionary_id
    FROM dictionary_words dw
    WHERE dw.word_id = :wordId
    LIMIT 1
""",
      nativeQuery = true)
  Integer findDictionaryId(@Param("wordId") Integer wordId);

  @Query(
      value =
          """
       SELECT
          w.id,
          w.eng_lang,
          w.rus_lang,
          w.transcription
       FROM words w
       JOIN dictionary_words dw ON dw.word_id = w.id
       WHERE dw.dictionary_id = :dictionaryId
        """,
      nativeQuery = true)
  List<WordDto> findWordsByDictionaryId(@Param("dictionaryId") Integer dictionaryId);
}

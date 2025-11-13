package com.example.repository;

import com.example.dto.WordDto;
import com.example.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
    @Query(value = """
    SELECT * FROM words
    WHERE id IN (
            SELECT id FROM words
                    ORDER BY RANDOM()
    LIMIT :limit
    )
    ORDER BY RANDOM()""", nativeQuery = true)
    List<Word> getWords(@Param("limit") int count);

    @Query(value = """
    SELECT w.id AS id,
           w.eng_lang AS engLang,      
           w.rus_lang AS rusLang,       
           w.transcription AS transcription,
           dw.dictionary_id AS dictionaryId
    FROM words w
    JOIN dictionary_words dw ON w.id = dw.word_id
    WHERE w.id NOT IN (
        SELECT word_id FROM cards_words WHERE user_id = :userId
    )
    ORDER BY RANDOM()
    LIMIT :limit
""", nativeQuery = true)
//    List<WordDto> getNewDeckWords(@Param("userId") int userId, @Param("limit") int limit);
    List<Object[]> getNewDeckWords(@Param("userId") int userId, @Param("limit") int limit);


}
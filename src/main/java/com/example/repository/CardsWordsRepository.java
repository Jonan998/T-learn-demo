package com.example.repository;

import com.example.model.CardsWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardsWordsRepository extends JpaRepository<CardsWords,Integer> {
    @Query("SELECT cw FROM CardsWords cw JOIN FETCH cw.user JOIN FETCH cw.word JOIN FETCH cw.dictionary WHERE cw.id = :id")
    Optional<CardsWords> getByIdCardWords(@Param("id") Integer id);

    @Query(value = """
    SELECT w.id,
           cw.study_lvl,
           w.eng_lang,
           w.rus_lang,
           w.transcription
    FROM cards_words cw
    JOIN words w ON w.id = cw.word_id
    WHERE cw.user_id = :userId
      AND cw.study_lvl > 0
      AND cw.next_review <= CURRENT_DATE
    ORDER BY RANDOM()
    LIMIT :limit
""", nativeQuery = true)
    List<Object[]> getRepeatDeckWords(@Param("userId") Integer userId,
                                      @Param("limit") int limit);

}

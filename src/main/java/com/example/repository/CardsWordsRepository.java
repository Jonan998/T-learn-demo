package com.example.repository;

import com.example.dto.WordDto;
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

    /**
     * Возвращает список слов для повтора (колоду повторения) для указанного пользователя.
     *
     * Метод делает следующее:
     * 1. Рассчитывает приоритет каждого слова с помощью модифицированного алгоритма забывания
     *    (экспоненциальная кривая + интервалы для каждого уровня изучения).
     * 2. Отбирает только те слова, у которых время следующего повторения уже наступило (next_review <= NOW()).
     * 3. Сортирует слова по приоритету в порядке убывания.
     * 4. Ограничивает результат указанным лимитом.
     *
     * @param userId идентификатор пользователя
     * @param limit максимальное количество возвращаемых слов
     * @return список DTO слов, отсортированных по приоритету
     */
    @Query(value = """
        SELECT 
            w.id AS id,
            cw.study_lvl AS studyLvl,
            w.eng_lang AS engLang,
            w.rus_lang AS rusLang,
            w.transcription AS transcription,
            (1 - EXP(
                -LN(2) * (EXTRACT(EPOCH FROM (NOW() - cw.next_review)) / 86400.0) /
                CASE 
                    WHEN cw.study_lvl = 1 THEN 1
                    WHEN cw.study_lvl = 2 THEN 2
                    WHEN cw.study_lvl = 3 THEN 4
                    WHEN cw.study_lvl = 4 THEN 7
                    WHEN cw.study_lvl = 5 THEN 20
                    WHEN cw.study_lvl = 6 THEN 90
                    ELSE 1
                END
            )) * (1 + 0.5 * (1.0 / (cw.study_lvl + 1))) AS priority
        FROM cards_words cw
        JOIN words w ON w.id = cw.word_id
        WHERE cw.user_id = :userId
          AND cw.next_review <= NOW()
        ORDER BY priority DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<WordDto> getRepeatDeckWords(
            @Param("userId") Integer userId,
            @Param("limit") int limit);

    Optional<CardsWords> findByUserIdAndWordId(int userId, int wordId);
}
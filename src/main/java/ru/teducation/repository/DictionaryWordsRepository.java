package ru.teducation.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.teducation.model.DictionaryWords;

@Repository
public interface DictionaryWordsRepository extends JpaRepository<DictionaryWords, Integer> {
  @Query(
      "SELECT dw FROM DictionaryWords dw JOIN FETCH dw.word JOIN FETCH dw.dictionary WHERE dw.id = :id")
  Optional<DictionaryWords> findByIdDict(@Param("id") Integer id);

  boolean existsByDictionaryIdAndWordId(int dictionaryId, int wordId);

  Optional<DictionaryWords> findByDictionaryIdAndWordId(int dictionaryId, int wordId);
}

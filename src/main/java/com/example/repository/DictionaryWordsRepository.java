package com.example.repository;

import com.example.model.DictionaryWords;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryWordsRepository extends JpaRepository<DictionaryWords, Integer> {
  @Query(
      "SELECT dw FROM DictionaryWords dw JOIN FETCH dw.word JOIN FETCH dw.dictionary WHERE dw.id = :id")
  Optional<DictionaryWords> findByIdDict(@Param("id") Integer id);
}

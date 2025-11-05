package com.example.repository;

import com.example.model.Dictionary_words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Dictionary_wordsRepository extends JpaRepository<Dictionary_words,Integer> {
    @Query("SELECT dw FROM Dictionary_words dw " +
            "JOIN FETCH dw.word " +
            "JOIN FETCH dw.dictionary " +
            "WHERE dw.id = :id")
    Optional<Dictionary_words> findByIdDict(@Param("id") Integer id);
}

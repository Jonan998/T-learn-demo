package com.example.repository;

import com.example.model.Cards_words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Cards_wordsRepository extends JpaRepository<Cards_words,Integer> {

    @Query("SELECT cw FROM Cards_words cw " +
            "JOIN FETCH cw.user " +
            "JOIN FETCH cw.word " +
            "JOIN FETCH cw.dictionary " +
            "WHERE cw.id = :id"
    )
    Optional<Cards_words> getByIdCardWords(@Param("id") Integer id);
}

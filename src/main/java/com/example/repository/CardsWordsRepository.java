package com.example.repository;

import com.example.model.CardsWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardsWordsRepository extends JpaRepository<CardsWords,Integer> {
    @Query("SELECT cw FROM Cards_words cw " +
            "JOIN FETCH cw.user " +
            "JOIN FETCH cw.word " +
            "JOIN FETCH cw.dictionary " +
            "WHERE cw.id = :id"
    )
    Optional<CardsWords> getByIdCardWords(@Param("id") Integer id);
}

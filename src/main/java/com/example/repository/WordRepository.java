package com.example.repository;

import com.example.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Integer> {
    @Query(value = """
    SELECT * FROM words
    WHERE id IN (
            SELECT id FROM words
                    ORDER BY RANDOM()
    LIMIT :limit
    )
    ORDER BY RANDOM()""", nativeQuery = true)
    List<Word> GetWords(@Param("limit") int count);
}
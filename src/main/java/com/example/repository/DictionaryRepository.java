package com.example.repository;

import com.example.dto.DictionaryDto;
import com.example.model.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary,Integer> {
    @Query(value = """
    SELECT 
        d.id AS id,
        d.name AS name,
        d.language AS language
    FROM dictionary d
    JOIN cards_words cw ON cw.dictionary_id = d.id
    WHERE cw.user_id = :userId
    GROUP BY d.id, d.name, d.language
    ORDER BY d.id ASC
""", nativeQuery = true)
    List<DictionaryDto> findUserDictionaries(@Param("userId") Integer userId);

}

package com.example.repository;

import com.example.model.Users_dictionaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Users_dictionariesRepository extends JpaRepository<Users_dictionaries,Integer> {
    @Query(
            "SELECT ud FROM Users_dictionaries ud " +
                    "JOIN FETCH ud.user " +
                    "JOIN FETCH ud.dictionary " +
                    "WHERE ud.id = :id"
    )
    Optional<Users_dictionaries> getByIdUsersDict(@Param("id") Integer id);
}

package com.example.repository;

import com.example.model.UsersDictionaries;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDictionariesRepository extends JpaRepository<UsersDictionaries, Integer> {
  @Query(
      "SELECT ud FROM UsersDictionaries ud JOIN FETCH ud.user JOIN FETCH ud.dictionary WHERE ud.id = :id")
  Optional<UsersDictionaries> getByIdUsersDict(@Param("id") Integer id);
}

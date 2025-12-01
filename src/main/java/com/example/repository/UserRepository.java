package com.example.repository;

import com.example.dto.UserLimitsView;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);

    @Query(value = """
    SELECT limit_new AS limitNew,
           limit_repeat AS limitRepeat
    FROM users
    WHERE id = :userId
    """, nativeQuery = true)

    UserLimitsView findUserLimits(@Param("userId") Integer userId);

}
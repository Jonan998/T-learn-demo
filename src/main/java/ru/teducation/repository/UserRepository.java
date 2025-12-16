package ru.teducation.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.teducation.dto.UserLimitsView;
import ru.teducation.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByName(String name);

  @Query(
      value =
          """
    SELECT limit_new AS limitNew,
           limit_repeat AS limitRepeat
    FROM users
    WHERE id = :userId
    """,
      nativeQuery = true)
  UserLimitsView findUserLimits(@Param("userId") Integer userId);
}

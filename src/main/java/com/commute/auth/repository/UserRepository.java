package com.commute.auth.repository;

import com.commute.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository {

  @Query("SELECT DISTINCT user FROM commute_user user " +
    "INNER JOIN FETCH user.roles AS roles " +
    "WHERE user.username = :username")
  User findByUsername(@Param("username") String username);
}

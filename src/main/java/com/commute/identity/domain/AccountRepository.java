package com.commute.identity.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, QuerydslPredicateExecutor<Account> {

  @Query("SELECT DISTINCT user FROM Account user " +
    "INNER JOIN FETCH user.roles AS roles " +
    "WHERE user.accountCredentials.username = :username")
  Account findByUsername(@Param("username") String username);


  Optional<Account> findOneById(Long userId);
}

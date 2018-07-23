package com.commute.auth.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserRepository,String> {
    UserRepository findByUserName(String userName);
}

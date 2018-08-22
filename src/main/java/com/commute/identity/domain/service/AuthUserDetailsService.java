package com.commute.identity.domain.service;

import com.commute.identity.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService {

  @Autowired
  private AccountRepository accountRepository;
}

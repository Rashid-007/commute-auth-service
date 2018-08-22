package com.commute.identity.security;

import com.commute.identity.domain.Account;
import com.commute.identity.domain.AccountRepository;
import com.commute.identity.domain.EmailAddress;
import com.commute.identity.domain.QAccount;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service("userDetailsService")
public class UserDetailsImp implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.debug("Loading account with username {} from database.", username);

    QAccount qAccount = QAccount.account;
    BooleanExpression byEmail = qAccount.accountCredentials.username.eq(new EmailAddress(username));
    Optional<Account> account = accountRepository.findOne(byEmail);//Optional.ofNullable(accountRepository.findOne(byEmail));

    /* @formatter:off */
    return account
      .map(a -> new CommuteSecurityContextAccount(a.getId(), a.getAccountCredentials().getUsername().getValue(),
        a.getAccountCredentials().getPassword(),
        a.isEmailVerified(),
        a.isEnabled(), true, true, true, // enabled, accountNonExpired, credentialsNonExpired, accountNonLocked
        a.grantedAuthorities()))
      .orElseThrow(() -> new UsernameNotFoundException(String.format("Account %s was not found in the database", username)));
    /* @formatter:on */
  }

}

package com.commute.identity.application;

import com.commute.identity.cfg.SystemProperties;
import com.commute.identity.domain.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.commute.identity.IdentityServiceError.EMAIL_ALREADY_USED;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdentityApplicationService {
  @NonNull
  private AccountRepository accountRepository;

  @NonNull
  private AccountService accountService;

  @NonNull
  private AuthenticationService authenticationService;

  @NonNull
  private ApplicationEventPublisher publisher;

  @Transactional(rollbackFor = Throwable.class)
  public AuthenticationDescriptor registerAccount(String email, String password){
    log.info("Creating commute domain account with email {}", email);
    BooleanExpression predicateByEmail = QAccount.account.accountCredentials.username.eq(new EmailAddress(email));

    if(accountRepository.exists(predicateByEmail)){
      throw new IllegalArgumentException(EMAIL_ALREADY_USED.build());
    }else {
      createAccount(email, password);

      return authenticate(email, password, true);
    }
  }

  private Account createAccount(String email, String password){
    Account account = accountService.saveAccount(new EmailAddress(email), password);
    account.verifyEmail();
    account = accountRepository.save(account);
    return account;
  }

  public AuthenticationDescriptor authenticate(String login, String password, boolean rememberMe) {
    AuthenticationDescriptor authenticationDescriptor = authenticationService.authenticate(new EmailAddress(login), password, rememberMe);

    publisher.publishEvent(new UserLoggedInEvent(authenticationDescriptor.getAccount().getId()));

    return authenticationDescriptor;
  }

}

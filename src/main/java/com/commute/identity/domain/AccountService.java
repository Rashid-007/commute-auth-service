package com.commute.identity.domain;

import com.commute.identity.domain.password.PasswordPolicyImp;
import com.commute.identity.domain.password.PasswordVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

import static com.commute.identity.IdentityServiceError.PASSWORD_NOT_VALID;

@Service
@Slf4j
public class AccountService {
  private PasswordEncoder passwordEncoder;

  private AccountRoleRepository roleRepository;

  private AccountRepository accountRepository;

  @Autowired
  public AccountService(PasswordEncoder passwordEncoder, AccountRoleRepository roleRepository, AccountRepository accountRepository){
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
    this.accountRepository = accountRepository;
  }
  public Account saveAccount(EmailAddress email, String password) {
    Assert.isTrue(isPasswordValid(password), PASSWORD_NOT_VALID.build());

    log.info("Saving account with username {}", email.getValue());

    AccountCredentials credentials = new AccountCredentials(email, passwordEncoder.encode(password));
    Account account = new Account(credentials);
    account = addUserRole(account);

    return accountRepository.save(account);

  }

  private Account addUserRole(Account account) {
    Optional<AccountRole> userRole = roleRepository.findById("ROLE_USER");
    account.addRole(userRole.get());
    return account;
  }

  private boolean isPasswordValid(String password) {
    return PasswordVerifier.verify(new PasswordPolicyImp(), password);
  }
}

package com.commute.identity.domain;

import com.commute.identity.cfg.SystemProperties;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
  @NonNull
  private AuthenticationManager authenticationManager;

  @NonNull
  private AccountRepository accountRepository;

  @NonNull
  private JwtTokenService jwtTokenService;

  @NonNull
  private SystemProperties systemProperties;

  @NonNull
  private RefreshTokenRepository refreshTokenRepository;

  @Transactional
  public AuthenticationDescriptor authenticate(EmailAddress email, String password, boolean rememberMe){
    Authentication authRequest = new UsernamePasswordAuthenticationToken(email.getValue(), password);
    authenticationManager.authenticate(authRequest); // throws exception if authentication fails

    QAccount qAccount = QAccount.account;
    BooleanExpression byEmail = qAccount.accountCredentials.username.eq(new EmailAddress(email));
    Optional<Account> account = accountRepository.findOne(byEmail);

    return account.map((a) -> createTokenCredentials(a, rememberMe))
      .orElseThrow(() -> new IllegalStateException("User account does not exists for " + email.getValue()));
  }


  private AuthenticationDescriptor createTokenCredentials(Account account, boolean rememberMe) {
    log.debug("Creating new token credentials for accountId {} and remember me {}", account.getId(), rememberMe);

    AuthenticationDescriptor accessToken = jwtTokenService.createAccessToken(account);
    account.updateLastLoginDate();

    if (rememberMe) {
      RefreshToken refreshToken = createNewRefreshToken(account.getId());
      accessToken.setRefreshToken(refreshToken);
    }

    return accessToken;
  }

  private RefreshToken createNewRefreshToken(Long accountId) {
    deleteRefreshToken(accountId);

    log.debug("Creating new refresh token for accountId {}", accountId);

    long tokenValidityInSeconds = systemProperties.getSecurity().getAuth().getRefreshToken().getTokenValidityInSeconds();

    LocalDateTime expiration = LocalDateTime.now().plusSeconds(tokenValidityInSeconds);

    RefreshToken refreshToken = new RefreshToken(accountId, expiration);

    return refreshTokenRepository.save(refreshToken);
  }

  public void deleteRefreshToken(Long accountId) {
    log.debug("Deleting refresh token for accountId {}", accountId);

    Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(accountId);

    if (refreshToken != null) {
      refreshTokenRepository.delete(refreshToken.get());
      refreshTokenRepository.flush();
    }
  }
}

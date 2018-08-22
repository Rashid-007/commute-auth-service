package com.commute.identity.domain;

import com.commute.identity.cfg.SystemProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

  // These are used throughout the distributed application. Changes should be coordinated across
  private static final String CLAIMS_ROLE_KEY = "role";
  private static final String CLAIMS_ACCOUNTID_KEY = "accountId";
  private static final String CLAIMS_EMAIL_VERIFIED_KEY = "emailVerified";
  @NonNull
  private SystemProperties systemProperties;

  /**
   * Creates a valid access token using a given
   * {@link com.commute.identity.domain.Account}
   *
   * @param account The account for which the access token should be created
   *
   * @return {@link AuthenticationDescriptor} representation of the created access token and it's
   *         related details.
   */
  AuthenticationDescriptor createAccessToken(Account account) {
    Assert.notNull(account, "account must not be null");

    LocalDateTime expiration = expirationDate();

    /* @formatter:off */
    String accessToken = Jwts.builder()
      .setSubject(account.getAccountCredentials().getUsername().getValue())
      .setExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)))
      .claim(CLAIMS_ACCOUNTID_KEY, account.getId().toString())
      .claim(CLAIMS_ROLE_KEY, account.combinedRoles())
      .claim(CLAIMS_EMAIL_VERIFIED_KEY, account.isEmailVerified())
      .signWith(SignatureAlgorithm.HS512,
        systemProperties.getSecurity().getAuth().getJwt().getSecret()).compact();
    /* @formatter:on */

    Long expiresIn = Duration.between(LocalDateTime.now(), expiration).getSeconds();

    return new AuthenticationDescriptor(accessToken, expiresIn, account);
  }

  /*
   * Creates a new {@link LocalDateTime} initialized with a calculated expiration date.
   */
  private LocalDateTime expirationDate() {
    long tokenValidityInSeconds = systemProperties.getSecurity().getAuth().getJwt().getTokenValidityInSeconds();

    return LocalDateTime.now().plusSeconds(tokenValidityInSeconds);
  }
}

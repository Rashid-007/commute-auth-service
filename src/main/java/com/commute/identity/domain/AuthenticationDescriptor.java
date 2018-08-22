package com.commute.identity.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

@ToString
@EqualsAndHashCode
public class AuthenticationDescriptor {
  @Getter
  private String accessToken;

  @Getter
  private Long expiresIn;

  @Getter
  @Setter
  private RefreshToken refreshToken;

  @Getter
  private Account account;

  /**
   * Package private constructor to prevent from direct usage in other package
   *
   * @param accessToken
   * @param expiresIn
   * @param account
   */
  AuthenticationDescriptor(String accessToken, Long expiresIn, Account account) {
    setAccessToken(accessToken);
    setExpiresIn(expiresIn);
    setAccount(account);
  }

  private void setAccessToken(String accessToken) {
    Assert.notNull(accessToken, "accessToken may not be null");

    this.accessToken = accessToken;
  }

  private void setExpiresIn(Long expiresIn) {
    Assert.notNull(expiresIn, "expiresIn may not be null");

    this.expiresIn = expiresIn;
  }

  private void setAccount(Account account) {
    Assert.notNull(account, "account may not be null");

    this.account = account;
  }
}

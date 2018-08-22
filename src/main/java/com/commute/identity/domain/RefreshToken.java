package com.commute.identity.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@EqualsAndHashCode(of = {"accountId"})
@Entity
@Table(name = "commute_refresh_token")
public class RefreshToken {
  @Id
  @Getter
  private Long accountId;

  @Getter
  @Column(unique = true)
  private UUID token;

  @Getter
  private LocalDateTime expiration;

  RefreshToken() {
    // To work with hibernate magic.
    super();
  }

  /**
   * Package private constructor to prevent from direct usage in other packages.
   *
   * @param accountId The commute account id
   * @param expiration The date time after the token expires
   */
  RefreshToken(Long accountId, LocalDateTime expiration) {
    Assert.notNull(accountId, "accountId must not be null");
    Assert.notNull(expiration, "expiration must not be null");

    this.accountId = accountId;
    this.expiration = expiration;

    token = UUID.randomUUID();
  }

  public void invalidate() {
    this.expiration = LocalDateTime.now();
  }

  public boolean isExpired() {
    return LocalDateTime.now().isAfter(expiration);
  }
}

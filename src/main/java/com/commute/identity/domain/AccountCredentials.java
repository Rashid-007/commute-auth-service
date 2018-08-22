package com.commute.identity.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.commute.identity.IdentityServiceError.*;


@Getter
@ToString(exclude = "password")
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "commute_account_credentials")
public class AccountCredentials {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  @NotNull
  @AttributeOverrides(@AttributeOverride(name = "value", column = @Column(name = "username", unique = true, nullable = false)))
  private EmailAddress username;

  @Getter
  @NotNull
  @Size(min = 60, max = 60) // encryption method generates a fix length char seq (bycrypt)
  @Column(name = "password_hash")
  private String password;  // hash-encoded password

  public AccountCredentials(){

  }

  public AccountCredentials(EmailAddress email, String password) {
    setUsername(email);
    setPassword(password);
  }

  private void setUsername(EmailAddress username) {
    Assert.notNull(username, USERNAME_NOT_VALID.build());

    this.username = username;
  }

  private void setPassword(String password){
    Assert.hasText(password, PASSWORD_NOT_VALID.build());

    this.password = password;

  }
  protected void changePassword(String newPassword) {
    Assert.state(!getPassword().equals(newPassword), NEW_PASSWORD_MUST_NOT_MATCH_WITH_CURRENT.build());

    setPassword(newPassword);
  }

  protected void changeEmail(EmailAddress newEmailAddress) {
    Assert.state(
        !getUsername().equals(newEmailAddress), NEW_EMAIL_ADDRESS_MUST_NOT_MATCH_CURRENT.build());

    setUsername(newEmailAddress);
    }

  }

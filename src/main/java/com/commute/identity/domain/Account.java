package com.commute.identity.domain;

import io.jsonwebtoken.lang.Assert;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Represents the end-user of commute service. Account uniquely identified using the id across the system.
 * Login id done using the login data and password
 */
@Getter
@NoArgsConstructor(access = AccessLevel.MODULE)
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "commute_account")
public class Account implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  private AccountCredentials accountCredentials;

  @Column(name = "account_expired")
  private boolean accountExpired;

  @Column(name = "account_locked")
  private boolean accountLocked;

  @Column(name = "credentials_expired")
  private boolean credentialsExpired;

  @Column(name = "enabled")
  @Setter(value = AccessLevel.PRIVATE)
  private boolean enabled;

  @Getter
  @Column(name = "creation_date", nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime creationTime;

  @Getter
  @Column(name = "created_by", nullable = false)
  @CreatedBy
  private String createdBy;

  @Getter
  @Column(name = "last_updated_date")
  @LastModifiedDate
  private LocalDateTime lastUpdatedDate;

  @Getter
  @Column(name = "last_updated_by")
  @LastModifiedBy
  private String lastUpdatedBy;

  @Getter
  @Column(name = "last_login_date")
  private LocalDateTime lastLoginDate;

  @Getter
  @Setter(value = AccessLevel.PRIVATE)
  @NotNull
  @Column(nullable = false)
  private boolean emailVerified;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "commute_account_account_role", joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_name", referencedColumnName = "name"))
  @OrderBy
  private Set<AccountRole> roles = new HashSet<>();

  public Account(AccountCredentials accountCredentials) {

    setAccountCredentials(accountCredentials);
    setEmailVerified(false);
  }

  public void setAccountCredentials(AccountCredentials accountCredentials) {
    Assert.notNull(accountCredentials, "accountCredentials must not be null");

    this.accountCredentials = accountCredentials;
  }

  public void verifyEmail() {
    this.setEmailVerified(true);
  }

  public void updateLastLoginDate() {
    this.lastLoginDate = LocalDateTime.now();
  }

  public String combinedRoles(){
    // @formatter:off
    return getRoles()
      .stream()
      .map(AccountRole::getName)
      .collect(joining(","));
    // @formatter:on
  }

  public void addRole(AccountRole anAccountRole) {
    this.getRoles().add(anAccountRole);
  }

  /**
   * Maps the {@link AccountRole}'s to the Spring specific Authority concept.
   *
   * @return {@link List < GrantedAuthority >} spring authorities
   */
  public List<SimpleGrantedAuthority> grantedAuthorities() {
    // @formatter:off
    return getRoles()
      .stream()
      .map(role -> new SimpleGrantedAuthority(role.getName()))
      .collect(toList());
    // @formatter:on
  }

}

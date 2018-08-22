package com.commute.identity.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@ToString
public class CommuteSecurityContextAccount extends User {
  private static final long serialVersionUID = 9186994501347496904L;

  @Getter
  private Long accountId;

  @Getter
  @Setter(AccessLevel.PRIVATE)
  private boolean emailVerified;

  public CommuteSecurityContextAccount(Long accountId, String username, String password, boolean emailVerified, String... authorities){
    super(username, password, Arrays.stream(authorities).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

    setEmailVerified(emailVerified);
    setAccountId(accountId);
  }

  public CommuteSecurityContextAccount(Long accountId, String username, String password, boolean emailVerified, boolean enabled, boolean accountNonExpired,
                        boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

    setEmailVerified(emailVerified);
    setAccountId(accountId);
  }

  private void setAccountId(Long accountId) {
    Assert.notNull(accountId, "AccountId must not be null");
    Assert.isTrue(accountId > 0, "AccountId must be greater than 0");

    this.accountId = accountId;
  }

  public String getEmail() {
    return this.getUsername();
  }
}

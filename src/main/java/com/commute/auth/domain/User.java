package com.commute.auth.domain;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "commute_user")
public class User implements UserDetails, Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  private UserCredentials userCredentials;

  @Column(name = "user_expired")
  private boolean accountExpired;

  @Column(name = "user_locked")
  private boolean accountLocked;

  @Column(name = "credentials_expired")
  private boolean credentialsExpired;

  private boolean enabled;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "commute_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_name", referencedColumnName = "name"))
  @OrderBy
  @JsonIgnore
  private Set<UserRole> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return userCredentials.getPassword();
  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !isAccountExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return !isAccountLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !isCredentialsExpired();
  }

}

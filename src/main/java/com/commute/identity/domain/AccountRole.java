package com.commute.identity.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "commute_account_role")
public class AccountRole implements Serializable {
  @Id
  @Size(min = 0, max = 50)
  @Getter
  @Setter(AccessLevel.PRIVATE)
  @Column(length = 50)
  private String name;

  public AccountRole(String anName) {
    this.setName(anName);
  }

  AccountRole() {
    // To work with hibernate magic.
    super();
  }
}

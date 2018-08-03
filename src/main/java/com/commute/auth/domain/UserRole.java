package com.commute.auth.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "commute_user_role")
public class UserRole {
  @Id
  @Size(min = 0, max = 50)
  @Getter
  @Setter(AccessLevel.PRIVATE)
  @Column(length = 50)
  private String name;
}

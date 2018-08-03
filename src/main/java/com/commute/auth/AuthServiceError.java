package com.commute.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum AuthServiceError implements ErrorCode {
  EMAIL_NOT_VALID(1, "E-mail address is not valid"),
  USERNAME_NOT_VALID (2, "Username is not valid"),
  PASSWORD_NOT_VALID(3, "Password is not valid"),
  NEW_PASSWORD_MUST_NOT_MATCH_WITH_CURRENT(4, "New password should not match the current password"),
  NEW_EMAIL_ADDRESS_MUST_NOT_MATCH_CURRENT(5, "New email address should not match the current one");
  private final int codePrefix = 2;
  private int codeNumber;

  @Setter
  private String errorMessage;
}

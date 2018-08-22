package com.commute.identity.application;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserLoggedInEvent {
  @Getter
  @NonNull
  private Long accountId;
}

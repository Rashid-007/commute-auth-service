package com.commute.identity.domain.password;

import org.passay.CharacterRule;
import org.passay.Rule;

import java.util.List;

public interface PasswordPolicy {
  List<Rule> getValidationRules();

  List<CharacterRule> getGenerationRules();

  int getGenerationLength();
}

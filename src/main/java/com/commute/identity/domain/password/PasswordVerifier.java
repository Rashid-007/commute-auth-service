package com.commute.identity.domain.password;

import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

public class PasswordVerifier {
  private PasswordVerifier() {
    // To avoid having the default public constructure
  }

  public static boolean verify(PasswordPolicy policy, String password){
    if (password != null) {
      PasswordValidator validator = new PasswordValidator(policy.getValidationRules());
      RuleResult result = validator.validate(new PasswordData(password));
      return result.isValid();
    }
    return false;
  }
}

package com.commute.identity.domain.password;

import org.passay.*;

import java.util.Arrays;
import java.util.List;

public class PasswordPolicyImp implements PasswordPolicy {
  private static final int MIN_LENGTH = 8;
  private static final int MAX_LENGTH = Integer.MAX_VALUE;

  /** Set of Rules for using a password**/
  private static final Rule LENGTH_RULE =  new LengthRule(MIN_LENGTH, MAX_LENGTH);
  private static final Rule WHITESPACE_RULE = new WhitespaceRule();
  private static final int LENGTH_GENERATOR = 12;
  private static final int MIN_CHARACTERISTICS = 2;


  private static final CharacterRule ALLOWED_GENERATOR_CHARACTERS_RULE = new CharacterRule(SpecificCharacterData.ENGLISH_WITH_MOBILE_FRIENDLY_SPECIAL_CHARS, 1);

  private static final CharacterRule LOWER_CHARS_RULE = new CharacterRule(SpecificCharacterData.ENGLISH_GERMAN_LOWER, 1);

  private static final CharacterRule UPPER_CHARS_RULE = new CharacterRule(SpecificCharacterData.ENGLISH_GERMAN_UPPER, 1);

  private static final CharacterRule DIGIT_RULE = new CharacterRule(EnglishCharacterData.Digit, 1);

  private static final CharacterRule SPECIALS_RULES = new CharacterRule(EnglishCharacterData.Special, 1);

  private static final CharacterRule SPECIALS_GENERATOR_RULE = new CharacterRule(SpecificCharacterData.MOBILE_FRIENDLY_SPECIAL_CHARS, 1);

  private static final List<CharacterRule> GENERATION_RULES =
    Arrays.asList(LOWER_CHARS_RULE, UPPER_CHARS_RULE, DIGIT_RULE, SPECIALS_RULES, SPECIALS_GENERATOR_RULE, ALLOWED_GENERATOR_CHARACTERS_RULE);

  private static final CharacterCharacteristicsRule CHARACTERISTICS_RULE;
  private static final List<Rule> VALIDATION_RULES;

  static {
    CHARACTERISTICS_RULE = new CharacterCharacteristicsRule();
    CHARACTERISTICS_RULE.setNumberOfCharacteristics(MIN_CHARACTERISTICS);
    CHARACTERISTICS_RULE.getRules().add(LOWER_CHARS_RULE);
    CHARACTERISTICS_RULE.getRules().add(UPPER_CHARS_RULE);
    CHARACTERISTICS_RULE.getRules().add(DIGIT_RULE);
    CHARACTERISTICS_RULE.getRules().add(SPECIALS_RULES);

    VALIDATION_RULES = Arrays.asList(LENGTH_RULE, CHARACTERISTICS_RULE, WHITESPACE_RULE);
  }

  @Override
  public List<Rule> getValidationRules() {
    return VALIDATION_RULES;
  }

  @Override
  public List<CharacterRule> getGenerationRules() {
    return GENERATION_RULES;
  }

  @Override
  public int getGenerationLength() {
    return LENGTH_GENERATOR;
  }
}

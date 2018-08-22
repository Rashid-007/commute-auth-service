package com.commute.identity.domain.password;

import org.passay.CharacterData;
import org.passay.EnglishCharacterData;

/**
 * Domain specific input data used by CharacterRule
 */
public enum SpecificCharacterData implements CharacterData {

  ENGLISH_GERMAN_LOWER(EnglishCharacterData.Alphabetical.getErrorCode(), EnglishCharacterData.LowerCase.getCharacters() + "äöü"),

  ENGLISH_GERMAN_UPPER(EnglishCharacterData.Alphabetical.getErrorCode(), EnglishCharacterData.UpperCase.getCharacters() + "ÄÖÜ"),

  MOBILE_FRIENDLY_SPECIAL_CHARS(EnglishCharacterData.Special.getErrorCode(), "!*+$"),

  ENGLISH_WITH_MOBILE_FRIENDLY_SPECIAL_CHARS(EnglishCharacterData.Special.getErrorCode(), EnglishCharacterData.Alphabetical.getCharacters() + EnglishCharacterData.Digit.getCharacters() + MOBILE_FRIENDLY_SPECIAL_CHARS);


  private final String errorCode;

  private final String characters;


  SpecificCharacterData(final String code, final String charString){
    errorCode = code;
    characters = charString;
  }

  @Override
  public String getErrorCode() {
    return errorCode;
  }

  @Override
  public String getCharacters() {
    return characters;
  }
}

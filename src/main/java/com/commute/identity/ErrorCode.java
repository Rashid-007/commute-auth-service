package com.commute.identity;

/**
 * The definition of Error codes.<br>
 * <br>
 * The convention for defining error codes is: {@code <DOMAIN_PREFIX>-<ERROR_CODE> <ERROR_MESSAGE>}.
 */
public interface ErrorCode {

  String ERROR_CODE_FORMAT = "%d-%d %s";
  String ERROR_CODE_REGEX = "(\\d+-\\d+) .*";
  //Pattern PATTERN = Pattern.compile(ERROR_CODE_REGEX);

  int getCodePrefix();

  int getCodeNumber();

  String getErrorMessage();

  default String build() {
    return String.format(ERROR_CODE_FORMAT, getCodePrefix(), getCodeNumber(), getErrorMessage());
  }

}

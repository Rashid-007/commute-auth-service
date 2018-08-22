package com.commute.identity.domain;

import lombok.*;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.Assert;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Locale;

import static com.commute.identity.IdentityServiceError.EMAIL_NOT_VALID;

@Embeddable
@ToString
@EqualsAndHashCode(callSuper = false)
public class EmailAddress implements Serializable {
  private static final long serialVersionUID = 1L;

  @Getter
  @NotNull
  private String value;

  EmailAddress(){
    super();
  }

  public EmailAddress(String address){
    super();
    this.setValue(address);
  }

  public EmailAddress(EmailAddress anEmailAddress) {
    this(anEmailAddress.getValue());
  }

  private void setValue(String address) {
    Assert.hasText(address, EMAIL_NOT_VALID.build());
    Assert.isTrue(EmailValidator.getInstance().isValid(address), EMAIL_NOT_VALID.build());
    Assert.isTrue(address.length() < 100, EMAIL_NOT_VALID.build());

    this.value = address.toLowerCase(Locale.ENGLISH);
  }

}

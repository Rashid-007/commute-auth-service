package com.commute.identity.web.rest.v1.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationDto {

  @ApiModelProperty(required = true, position = 0)
  private String tokenType;

  @ApiModelProperty(required = true, position = 1)
  private String jwt;

  @ApiModelProperty(required = true, position = 2)
  private Long expiresIn;

  @ApiModelProperty(required = false, position = 3)
  private String refreshToken;

  @ApiModelProperty(required = true, position = 4)
  private Long accountId;
}

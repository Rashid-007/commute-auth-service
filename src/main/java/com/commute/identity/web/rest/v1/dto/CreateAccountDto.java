package com.commute.identity.web.rest.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateAccountDto {

  @ApiModelProperty(required = true, example = "example@commute.com", position = 0)
  private String email;

  @ApiModelProperty(value = "User password", example = "Password$0", position = 1)
  private String password;
}

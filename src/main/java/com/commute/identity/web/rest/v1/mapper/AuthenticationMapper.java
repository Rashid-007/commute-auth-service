package com.commute.identity.web.rest.v1.mapper;

import com.commute.identity.domain.AuthenticationDescriptor;
import com.commute.identity.web.rest.v1.dto.AuthenticationDto;

public class AuthenticationMapper {
  public static final String TOKEN_TYPE = "bearer";

  public static AuthenticationDto toDto(AuthenticationDescriptor descriptor) {
    AuthenticationDto dto = new AuthenticationDto();
    dto.setTokenType(TOKEN_TYPE);
    dto.setJwt(descriptor.getAccessToken());
    dto.setExpiresIn(descriptor.getExpiresIn());
    dto.setAccountId(descriptor.getAccount().getId());

    if (descriptor.getRefreshToken() != null) {
      dto.setRefreshToken(descriptor.getRefreshToken().getToken().toString());
    }

    return dto;
  }
}

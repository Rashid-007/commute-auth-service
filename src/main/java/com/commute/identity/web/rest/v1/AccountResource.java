package com.commute.identity.web.rest.v1;

import com.commute.identity.application.IdentityApplicationService;
import com.commute.identity.cfg.SystemProperties;
import com.commute.identity.domain.AuthenticationDescriptor;
import com.commute.identity.web.rest.v1.dto.AuthenticationDto;
import com.commute.identity.web.rest.v1.dto.CreateAccountDto;
import com.commute.identity.web.rest.v1.mapper.AuthenticationMapper;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class AccountResource {

  @NonNull
  private IdentityApplicationService applicationService;

  @NonNull
  private SystemProperties systemProperties;


  @ApiOperation(value = "Register account")
  @RequestMapping(value =  "/accounts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<AuthenticationDto> user(@RequestBody CreateAccountDto accountDto) {

    AuthenticationDescriptor authenticationDescriptor = applicationService.registerAccount(accountDto.getEmail(), accountDto.getPassword());
    AuthenticationDto dto = AuthenticationMapper.toDto(authenticationDescriptor);

    return ResponseEntity.status(HttpStatus.CREATED)
      .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", dto.getJwt()))
      .body(dto);

  }
}

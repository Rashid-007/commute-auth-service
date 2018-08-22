package com.commute.identity.security;

import com.commute.identity.cfg.SystemProperties;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *  Adds the {@link JsonWebTokenFilter} to the Http filter chain
 */
public class JsonWebTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private SystemProperties systemProperties;

  public JsonWebTokenFilterConfigurer(SystemProperties systemProperties){
    this.systemProperties = systemProperties;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    JsonWebTokenFilter customFilter = new JsonWebTokenFilter(systemProperties);
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }
}

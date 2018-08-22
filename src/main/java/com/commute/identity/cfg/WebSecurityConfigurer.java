package com.commute.identity.cfg;

import com.commute.identity.security.JsonWebTokenFilterConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 11)
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

  @Autowired
  private SystemProperties systemProperties;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf()
      .disable()
      .headers()
      .frameOptions()
      .disable()
     .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
     .and()
      .authorizeRequests()
      .antMatchers("/management/health").permitAll()
      .antMatchers(HttpMethod.POST, "/api/v1/accounts/**").permitAll()
      .antMatchers("/api/**").authenticated()
    .and()
      .apply(new JsonWebTokenFilterConfigurer(systemProperties));
  }
}


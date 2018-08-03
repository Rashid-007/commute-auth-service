package com.commute.auth.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Encoders {

  @Bean
  public PasswordEncoder clientPasswordEncoder(){
    return new BCryptPasswordEncoder(4); // With strength 4. The larger, the more work
  }

  @Bean
  public PasswordEncoder userPasswordEncoder(){
    return new BCryptPasswordEncoder(8);
  }
}

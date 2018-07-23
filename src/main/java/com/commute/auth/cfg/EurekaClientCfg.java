package com.commute.auth.cfg;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableEurekaClient
@Profile("!" + Constants.SPRING_PROFILE_NO_EUREKA)
public class EurekaClientCfg {
}

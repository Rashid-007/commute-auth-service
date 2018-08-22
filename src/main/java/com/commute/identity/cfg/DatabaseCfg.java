package com.commute.identity.cfg;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseCfg {
  private final Logger log = LoggerFactory.getLogger(DatabaseCfg.class);

  @Bean(initMethod = "migrate")
  Flyway flyway(DataSource dataSource) {
    Flyway flyway = new Flyway();
    flyway.setBaselineOnMigrate(true);
    flyway.setLocations("db/migration");
    flyway.setDataSource(dataSource);

    return flyway;
  }
}

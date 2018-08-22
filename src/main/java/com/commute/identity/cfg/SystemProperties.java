package com.commute.identity.cfg;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "commute")
@Setter
@Getter
public class SystemProperties {
    private final Security security = new Security();
    private final Swagger swagger = new Swagger();

    @Setter
    @Getter
    public static class Security {
      private Auth auth = new Auth();

      @Setter
      @Getter
      public static class Auth{

        private Jwt jwt = new Jwt();
        private RefreshToken refreshToken = new RefreshToken();

        @Getter
        @Setter
        public static class Jwt{
          private String secret;
          private long tokenValidityInSeconds;
        }
        @Getter
        @Setter
        public static class RefreshToken {
          private long tokenValidityInSeconds;
        }

      }
    }

    @Setter
    @Getter
    public static class Swagger {
        private String title;
        private String description;
        private Boolean enabled;
    }

}

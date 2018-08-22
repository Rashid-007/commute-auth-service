package com.commute.identity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
public class CreateJWT {

  @Test
  public void testJWT() {
   /* @formatter:off */
    String jwt = Jwts.builder()
      .setSubject("enayet@enayet.de")
      .setExpiration(Date.from(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.UTC)))
      .claim("accountId", "1")
      .claim("role", "ROLE_USER,ROLE_ADMIN,ROLE_OPERATIONS,ACTUATOR")
      .claim("emailVerified", true)
      .signWith(SignatureAlgorithm.HS512, "SDJSK2E7Tj5kTUt7I2N1TDJMQFdzaVpaI1V4IUtWLDVfLSY0SH01Jm0mZlBqcWMoelByZXtFN1VdcigsaXN4NFInWVw1VSxIZDVcKDx+Njc7ZUVcTX0/JVszXkMrZyU7d3kiQnVyTlV4emMmeTx0X2VWOWs1XUpISFRnL1V9W2QK")
      //.signWith(SignatureAlgorithm.HS512, "XmprZ1Y5XTVOfSc6Mnp4IzcnXzVSJC9YRnEkWS5FNXcsWz5IZ0Ypckg1e0UnRSRNbmpeeEVbNk5nZ2tDe2omZV1MM2stZnlLO2V9fmE7TkN0LTQpSjgiPjM3dGpWaHY8TEE/PS4zUlNOUzldYTtqJlNKUXE8bUVRdGhbJEYrbl1NX2ZzdnFUNH4zOXRtIlZNXjVmLFstaEg+L2lUb2pFWTJURE08XFctXERxZl9vXnNZIT1hbSd7ZlEpX2N4S2ZEdHdFRWB3XEFwR1YsMkd0ImV5IWBUNlVLdysjUGdAPlBRdiR5Ozk+fUdDNCprQC4pNW1NM0suWSdYTDVLNi8heQo=\n")
      .compact();

    log.info(jwt);
   /* @formatter:on */

   System.out.println(this.getClass().getSimpleName());

  }

  @Test
  public void generateSecret() {
    SecretKey secretKey = MacProvider.generateKey();
    byte[] keyBytes = secretKey.getEncoded();
    String base64Encoded = TextCodec.BASE64.encode(keyBytes);

  }
}

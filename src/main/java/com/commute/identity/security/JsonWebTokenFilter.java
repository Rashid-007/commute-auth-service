package com.commute.identity.security;

import com.commute.identity.cfg.SystemProperties;
import io.jsonwebtoken.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * A general {@link javax.servlet.Filter} to extract a Json Web Token (JWT) and to create a spring
 * {@link Authentication}-object if available. It's populated by passing it to the
 * {@link SecurityContextHolder}.<br>
 * <br>
 * It is assumed that the JWT gets passed as a Http-Header with the key {@code "Authorization"}
 * and prefixed by {@code "Bearer "}.
 *
 * @see org.springframework.security.web.context.SecurityContextPersistenceFilter
 */
public class JsonWebTokenFilter extends GenericFilterBean {

  public static final String CLAIMS_ACCOUNTID_KEY = "accountId";
  public static final String CLAIMS_ROLE_KEY = "role";
  public static final String CLAIMS_EMAILVERIFIED_KEY = "emailVerified";

  private SystemProperties systemProperties;

  public JsonWebTokenFilter(SystemProperties systemProperties){
    this.systemProperties = systemProperties;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String jwt = resolveToken(httpServletRequest);

    if (StringUtils.isNotBlank(jwt)) {
      parseAndValidateJwt(jwt).ifPresent(claims -> {
        if(validateClaims(claims)){
          SecurityContextHolder.getContext().setAuthentication(createAuthentication(claims));
        }else {
          logger.warn(String.format("Valid JWT contains invalid claims. Caution for any security tapping Supplied claims: %s", claims));
        }
      });
    }
    chain.doFilter(request, response);
  }

  /**
   * Extract token by parsing the Authorization header
   * @param request
   * @return
   */
  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }

    return null;
  }

  private Optional<Claims> parseAndValidateJwt(String jwt) {
    try {
      return Optional.of(Jwts.parser().setSigningKey(systemProperties.getSecurity().getAuth().getJwt().getSecret())
        .parseClaimsJws(jwt).getBody());
    } catch (MalformedJwtException e) {
      logger.warn("JWT is not well formed (warning: may be a security breach).", e);
    } catch (SignatureException e) {
      logger.warn("Signature of JWT is not valid  (warning: may be a security breach).", e);
    } catch (ExpiredJwtException e) {
      logger.info("JWT has expired.", e);
    } catch (Exception e) {
      logger.warn("JWT is not valid", e);
    }

    // Move on in case of a exception without any AuthenticationToken => request is not
    // authenticated.
    return Optional.empty();
  }

  private boolean validateClaims(Claims claims) {
    boolean valid = true;

    if (!claims.containsKey(CLAIMS_ACCOUNTID_KEY) || !StringUtils.isNumeric(String.valueOf(claims.get(CLAIMS_ACCOUNTID_KEY)))) {
      valid = false;
    }

    if (!claims.containsKey(CLAIMS_ROLE_KEY) || !(claims.get(CLAIMS_ROLE_KEY) instanceof String)) {
      valid = false;
    }

    if (claims.containsKey(CLAIMS_EMAILVERIFIED_KEY) && !(claims.get(CLAIMS_EMAILVERIFIED_KEY) instanceof Boolean)) {
      valid = false;
    }

    return valid;
  }

  private Authentication createAuthentication(Claims claims) {
    Long accountId = Long.parseLong(String.valueOf(claims.get(CLAIMS_ACCOUNTID_KEY)));
    String[] roles = claims.get(CLAIMS_ROLE_KEY, String.class).split(",");

    boolean emailVerified = false;

    if (claims.containsKey(CLAIMS_EMAILVERIFIED_KEY)) {
      emailVerified = claims.get(CLAIMS_EMAILVERIFIED_KEY, Boolean.class);
    }

    CommuteSecurityContextAccount contextAccount = new CommuteSecurityContextAccount(accountId, claims.getSubject(), "", emailVerified, roles);

    return new UsernamePasswordAuthenticationToken(contextAccount, "", contextAccount.getAuthorities());
  }
}

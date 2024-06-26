package victor.training.spring.security.config.keycloak;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class TokenUtils {
  public static void printTheTokens() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!(principal instanceof DefaultOidcUser oidcUser)) {
      return;
    }
//    JwtAuthenticationToken t= oidcUser;

    log.info("\n-- OpenID Connect Token: {} ", oidcUser.getUserInfo().getClaims());
    Instant expInstant = oidcUser.getExpiresAt();
    LocalDateTime expirationTime = expInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    String deltaLeft = LocalTime.MIN.plusSeconds(LocalDateTime.now().until(expirationTime, ChronoUnit.SECONDS)).toString();
    log.info("User: " + oidcUser);
    log.info("\n-- Access Token 👑 (expires in {} at {}): {}\n{}",
        deltaLeft,
        expirationTime,
        getCurrentToken().orElse("N/A"),
        mapToPrettyJson(oidcUser.getAttributes()));
  }

  public static Optional<String> getCurrentToken() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .filter(OidcUser.class::isInstance)
        .map(OidcUser.class::cast)
        .map(OidcUser::getIdToken)
        .map(OidcIdToken::getTokenValue);
  }

  private static String mapToPrettyJson(Map<String, Object> map) {
    return map.entrySet().stream().sorted(Map.Entry.comparingByKey())
        .map(e -> "\t" + e.getKey() + ": " + e.getValue())
        .collect(Collectors.joining("\n"));
  }

}

package victor.training.spring.security.config.keycloak;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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
  }

  public static Optional<String> getCurrentToken() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getDetails)
        .map(Object::toString);
  }

  private static String mapToPrettyJson(Map<String, Object> map) {
    return map.entrySet().stream().sorted(Map.Entry.comparingByKey())
        .map(e -> "\t" + e.getKey() + ": " + e.getValue())
        .collect(Collectors.joining("\n"));
  }

}

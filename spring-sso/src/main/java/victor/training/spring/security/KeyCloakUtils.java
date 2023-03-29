package victor.training.spring.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.TimeZone;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;
import static java.time.format.DateTimeFormatter.ofPattern;

@Slf4j
public class KeyCloakUtils {
    public static void printTheTokens() {
        Object opaquePrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(opaquePrincipal instanceof KeycloakPrincipal)){
            return;
        }
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) opaquePrincipal;
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();

        log.info("\n-- OpenID Connect Token 👑: {} " , context.getIdTokenString());
        LocalDateTime expirationTime = Instant.ofEpochMilli(context.getToken().getExp() * 1000L)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        String deltaLeft  = LocalTime.MIN.plusSeconds(LocalDateTime.now().until(expirationTime, ChronoUnit.SECONDS)).toString();
        log.info("\n-- Access Token 👑 (expires in {} at {}): {}\n{}",
                deltaLeft,
                expirationTime,
                context.getTokenString(),
                prettyFormatJWT(context.getTokenString()));
    }

    private static String prettyFormatJWT(String jwtString) {
        String jwtBodyString = jwtString.split("\\.")[1];
        String decodedBody = new String(Base64.getUrlDecoder().decode(jwtBodyString));
        try {
            Object json = new ObjectMapper().readValue(decodedBody, Object.class);
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

package victor.training.spring.web.security.keycloak;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Base64;

@Slf4j
public class KeyCloakUtils {
    public static void printTheTokens() {
        Object opaquePrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) opaquePrincipal;
        KeycloakSecurityContext keycloakSecurityContext = principal.getKeycloakSecurityContext();

        log.info("\n-- OpenID Connect Token 👑: {}\n{}",
                keycloakSecurityContext.getIdTokenString(),
                prettyFormatJWT(keycloakSecurityContext.getIdTokenString()));
        log.info("\n-- Access Token 👑: {}\n{}",
                keycloakSecurityContext.getTokenString(),
                prettyFormatJWT(keycloakSecurityContext.getTokenString()));
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

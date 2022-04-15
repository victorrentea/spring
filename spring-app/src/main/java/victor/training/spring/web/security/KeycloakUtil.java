package victor.training.spring.web.security;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class KeycloakUtil {
   @SuppressWarnings("unchecked")
   public static KeycloakSecurityContext securityContext() {
      KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>)
          SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return principal.getKeycloakSecurityContext();
   }
}

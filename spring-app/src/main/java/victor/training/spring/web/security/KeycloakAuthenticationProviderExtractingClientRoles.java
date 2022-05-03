package victor.training.spring.web.security;

import org.keycloak.adapters.springsecurity.account.KeycloakRole;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken.Access;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;


public class KeycloakAuthenticationProviderExtractingClientRoles implements AuthenticationProvider {
   private final String keycloakClientName;

   public KeycloakAuthenticationProviderExtractingClientRoles(String keycloakClientName) {
      this.keycloakClientName = keycloakClientName;
   }

   @Override
   public Authentication authenticate(Authentication authentication) throws AuthenticationException {
      KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;
      List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

      for (String role : token.getAccount().getRoles()) {
         grantedAuthorities.add(new KeycloakRole(role));
      }
      Access access = token.getAccount().getKeycloakSecurityContext().getToken().getResourceAccess().get(keycloakClientName);
      if (access!=null) {
         for (String role : access.getRoles()) {
            grantedAuthorities.add(new KeycloakRole(role));
         }
      }
      return new KeycloakAuthenticationToken(token.getAccount(), token.isInteractive(), grantedAuthorities);
   }

   @Override
   public boolean supports(Class<?> aClass) {
      return KeycloakAuthenticationToken.class.isAssignableFrom(aClass);
   }
}

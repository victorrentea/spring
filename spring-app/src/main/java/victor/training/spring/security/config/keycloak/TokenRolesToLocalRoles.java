package victor.training.spring.security.config.keycloak;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import victor.training.spring.web.entity.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toUnmodifiableSet;

@Slf4j
public class TokenRolesToLocalRoles implements GrantedAuthoritiesMapper {
  public enum RoleLevel {
    /**
     * Global per ecosystem, in OIDCToken.realm_access.roles
     */
    REALM,
    /**
     * Specific to my system, in OIDCToken.resource_access['spring-app'].roles
     */
    CLIENT
  }

  private final boolean expandRoles;

  public TokenRolesToLocalRoles(RoleLevel roleLevel, boolean expandRoles) {
    this.roleLevel = roleLevel;
    this.expandRoles = expandRoles;
  }

  private final RoleLevel roleLevel;

  @Override
  public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {

    if (authorities.isEmpty()) {
      throw new IllegalArgumentException("No OIDC authorities => no way to get to OIDC Token");
    }
    GrantedAuthority firstAuthority = authorities.iterator().next();

    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) firstAuthority;

    OidcIdToken idToken = oidcUserAuthority.getIdToken();

    List<String> tokenRoles = switch (roleLevel) {
      case REALM -> getRealmRoles(idToken);
      case CLIENT -> getClientRoles(idToken, "spring-app");
    };

    List<String> localRoles;
    if (expandRoles) {
      localRoles = UserRole.expandToSubRoles(tokenRoles);
      log.debug("Expanded roles: {}", tokenRoles);
    } else {
      localRoles = tokenRoles;
    }
    log.debug("Token roles: {} => Local roles: {} ", tokenRoles, localRoles);
    return localRoles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(toUnmodifiableSet());
  }

  private List<String> getRealmRoles(OidcIdToken idToken) {
    Map<String, List<String>> realmAccess = requireNonNull(idToken.getClaim("realm_access"), "No realm_access claim in OIDC Token");
    return requireNonNull(realmAccess.get("roles"), "No roles in realm_access claim in ODIC Token");
  }

  private List<String> getClientRoles(OidcIdToken idToken, String clientName) {
    Map<String, Map<String, List<String>>> resourceAccess = requireNonNull(idToken.getClaim("resource_access"), "No resource_access claim in OIDC Token");
    Map<String, List<String>> clientAccess = requireNonNull(resourceAccess.get(clientName), "No resource_access['spring-app'] claim in OIDC Token");
    return requireNonNull(clientAccess.get("roles"), "No roles in resource_access['spring-app'] claim in ODIC Token");
  }
}

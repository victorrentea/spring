//package victor.training.spring.security.config.keycloak;
//
//import lombok.extern.slf4j.Slf4j;
//import org.keycloak.adapters.springsecurity.account.KeycloakRole;
//import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
//import org.keycloak.representations.AccessToken;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
//
//import java.util.*;
//
//@Slf4j
//public class KeycloakResourceAuthenticationProvider implements AuthenticationProvider {
//    private GrantedAuthoritiesMapper grantedAuthoritiesMapper;
//    private final String clientName;
//
//    public KeycloakResourceAuthenticationProvider(String clientName) {
//        this.clientName = Objects.requireNonNull(clientName);
//    }
//
//    public void setGrantedAuthoritiesMapper(GrantedAuthoritiesMapper grantedAuthoritiesMapper) {
//        this.grantedAuthoritiesMapper = grantedAuthoritiesMapper;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;
//
//        AccessToken accessToken = token.getAccount().getKeycloakSecurityContext().getToken();
//        AccessToken.Access resourceAccess = accessToken.getResourceAccess(clientName);
//        if (resourceAccess == null || resourceAccess.getRoles().isEmpty()) {
//            KeyCloakUtils.printTheTokens();
//            log.error("No resource_access roles found in ID token for client application: " + clientName);
//            throw new InsufficientAuthenticationException("No resource_access roles found for client application: " + clientName);
//        }
//
//        Set<String> rolesForClient = resourceAccess.getRoles();
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for (String role : rolesForClient) {
//            grantedAuthorities.add(new KeycloakRole(role));
//        }
//        return new KeycloakAuthenticationToken(token.getAccount(), token.isInteractive(), mapAuthorities(grantedAuthorities));
//    }
//
//    private Collection<? extends GrantedAuthority> mapAuthorities(
//            Collection<? extends GrantedAuthority> authorities) {
//        return grantedAuthoritiesMapper != null
//                ? grantedAuthoritiesMapper.mapAuthorities(authorities)
//                : authorities;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return KeycloakAuthenticationToken.class.isAssignableFrom(aClass);
//    }
//}
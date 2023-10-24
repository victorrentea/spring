package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.security.config.keycloak.KeyCloakUtils;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SecurityController {
  private final AnotherClass anotherClass;

  @GetMapping("api/user/current")
  public CurrentUserDto getCurrentUsername(
        /*HttpSession*/
        Principal principal// limitation: auditing columns LAST_MODIFIED_BY set in the repo
  ) throws Exception {
    KeyCloakUtils.printTheTokens();

    log.info("Return current user");
    CurrentUserDto dto = new CurrentUserDto();
    MDC.put("key","value");
    dto.username = serviceMethod(principal);
    // dto.username = anotherClass.asyncMethod().get();
    // dto.role = extractOneRole(authentication.getAuthorities());

    // dto.authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    //<editor-fold desc="KeyCloak">
//    		dto.username = authentication.getName();
//    		dto.role = authentication.getAuthorities().iterator().next().getAuthority();
//    		dto.authorities = stripRolePrefix(authentication.getAuthorities());
//        // Optional:
//    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    		KeycloakPrincipal<KeycloakSecurityContext> keycloakToken =(KeycloakPrincipal<KeycloakSecurityContext>)
//            authentication.getPrincipal();
//    		dto.fullName = keycloakToken.getKeycloakSecurityContext().getIdToken().getName();
//    		log.info("Other details about user from ID Token: " + keycloakToken.getKeycloakSecurityContext().getIdToken().getOtherClaims());
    //</editor-fold>
    return dto;
  }

  private String serviceMethod(Principal principal) {
    return repoMethod(principal);
  }

  private String repoMethod(Principal principal) {
//    String key = MDC.get("key");
//    return principal.getName();
    // to avoid passing Principal everywhere:

    // Magic: Spring can give you the username currently executing this function RIGHT NOW.
    // even if there are multiple executions of the same function ongoing
    // magic = ThreadLocal (java construct allowing to bind some data to the current thread)
    //    , storing:
    // - @Transactional / JDBC Connection
    // - SecurityContextHolder
    // - Logback.MDC
    // - TraceID
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    List<String> strings = authentication.getAuthorities()
    return authentication.getName();
  }

  public static String extractOneRole(Collection<? extends GrantedAuthority> authorities) {
    // For Spring Security (eg. hasRole) a role is an authority starting with "ROLE_"
    List<String> roles = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .filter(authority -> authority.startsWith("ROLE_"))
            .map(authority -> authority.substring("ROLE_".length()))
            .toList();
    if (roles.size() == 2) {
      log.debug("Even though Spring allows a user to have multiple roles, we wont :)");
      return "N/A";
    }
    if (roles.size() == 0) {
      return null;
    }
    return roles.get(0);
  }


  //    	@Bean // enable propagation of SecurityContextHolder over @Async
  //    	public DelegatingSecurityContextAsyncTaskExecutor taskExecutor(ThreadPoolTaskExecutor executor) {
  //    		// https://www.baeldung.com/spring-security-async-principal-propagation
  //    		return new DelegatingSecurityContextAsyncTaskExecutor(executor);
  //    	}

  @Slf4j
  @Service
  public static class AnotherClass {
    //    @Async
    //    public CompletableFuture<String> asyncMethod() {
    //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //        log.info("Current authentication = {}", authentication);
    //        return CompletableFuture.completedFuture(authentication.getName());
    //    }
  }

}

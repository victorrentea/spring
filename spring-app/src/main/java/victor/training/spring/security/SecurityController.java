package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.security.config.keycloak.KeyCloakUtils;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SecurityController {
  private final AnotherClass anotherClass;

//  private final ThreadPoolTaskExecutor taskExecutor;
  private final TaskExecutor taskExecutor;

  @GetMapping("api/user/current")
  public CurrentUserDto getCurrentUsername() throws Exception {
    KeyCloakUtils.printTheTokens();

    log.info("Return current user");
    CurrentUserDto dto = new CurrentUserDto();
    // a STATIC method gives access to the CURRENT user now runnig the request
    // what if there are 10 parallel requests !??!
    // IS BOUND TO THE CURRENT THREAD in a "ThreadLocal" variable
//    dto.username = SecurityContextHolder.getContext().getAuthentication().getName();

//    to loose it do this:

    CompletableFuture<String> userFromAnotherThread = CompletableFuture.supplyAsync(() -> {
      log.info("WHere am I");
      return SecurityContextHolder.getContext().getAuthentication().getName();
    }, taskExecutor); // security context propagates over async calls done via decorated SPRING task executor
    dto.username = userFromAnotherThread.get();

    // dto.username = anotherClass.asyncMethod().get();

    // A) role-based security
//    dto.role = extractOneRole(authentication.getAuthorities());

    // B) authority-based security
//    dto.authorities = authentication.getAuthorities().stream()
//            .map(GrantedAuthority::getAuthority)
//            .collect(Collectors.toList());

    //<editor-fold desc="KeyCloak">
    //		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //		dto.username = authentication.getName();
    //		dto.role = authentication.getAuthorities().iterator().next().getAuthority();
    //		dto.authorities = stripRolePrefix(authentication.getAuthorities());
    //    // Optional:
    //		KeycloakPrincipal<KeycloakSecurityContext> keycloakToken =(KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
    //		dto.fullName = keycloakToken.getKeycloakSecurityContext().getIdToken().getName();
    //		log.info("Other details about user from ID Token: " + keycloakToken.getKeycloakSecurityContext().getIdToken().getOtherClaims());
    //</editor-fold>
    return dto;
  }

  public static String extractOneRole(Collection<? extends GrantedAuthority> authorities) {
    // For Spring Security (eg. hasRole) a role is an authority starting with "ROLE_"
    List<String> roles = authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .filter(authority -> authority.startsWith("ROLE_"))
        .map(authority -> authority.substring("ROLE_".length()))
        .collect(Collectors.toList());
    if (roles.size() == 2) {
      log.debug("Even though Spring allows a user to have multiple roles, we wont :)");
      return "N/A";
    }
    if (roles.size() == 0) {
      return null;
    }
    return roles.get(0);
  }



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

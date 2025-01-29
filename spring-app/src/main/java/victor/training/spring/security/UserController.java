package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import victor.training.spring.security.config.keycloak.KeyCloakUtils;
import victor.training.spring.security.config.keycloak.TokenUtils;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
  private final AnotherClass anotherClass;

  @GetMapping("api/user/current")
  public CurrentUserDto getCurrentUsername() throws ExecutionException, InterruptedException {
    TokenUtils.printTheTokens();

    log.info("Return current user");
    CurrentUserDto dto = new CurrentUserDto();
    dto.username = anotherClass.altaMetoda().get(); // TODO
//SecurityContextHolder.getContext().getAuthentication().getAuthorities() // roluri
    //<editor-fold desc="KeyCloak">
    //		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //		dto.username = authentication.getName();
    //		dto.role = authentication.getSubRoles().iterator().next().getAuthority();
    //		dto.authorities = stripRolePrefix(authentication.getSubRoles());
    //    // Optional:
    //		KeycloakPrincipal<KeycloakSecurityContext> keycloakToken =(KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
    //		dto.fullName = keycloakToken.getKeycloakSecurityContext().getIdToken().getName();
    //		log.info("Other details about user from ID Token: " + keycloakToken.getKeycloakSecurityContext().getIdToken().getOtherClaims());
    //</editor-fold>
    return dto;
  }

    @Bean // enable propagation of SecurityContextHolder over @Async
  	public DelegatingSecurityContextAsyncTaskExecutor taskExecutor(ThreadPoolTaskExecutor poolBar) {
  		return new DelegatingSecurityContextAsyncTaskExecutor(poolBar);
   	}

  @Slf4j
  @Service
  public static class AnotherClass {
    @Async("taskExecutor")
    public CompletableFuture<String> altaMetoda() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//      ((KeyCloackPrincipal)authentication.getPrincipal()).getClaim("preferred_username");
      String username = authentication.getName();
      System.out.println("UPDATED_BY="+username);
      return CompletableFuture.completedFuture(username);
    }
  }
}

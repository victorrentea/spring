package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
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

import java.security.Principal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
  private final AnotherClass anotherClass;
  private final ThreadPoolTaskExecutor e;

  @GetMapping("api/user/current")
  public CurrentUserDto getCurrentUsername() throws ExecutionException, InterruptedException {
    TokenUtils.printTheTokens();

    log.info("Return current user");
    CurrentUserDto dto = new CurrentUserDto();
    // sumar: cand schimb threadul pierzi: @Transactional, SecurityContext, TraceId
//    Authentication authentication = CompletableFuture.supplyAsync(() -> f(),e).get();

    dto.username = SecurityContextHolder.getContext().getAuthentication().getName();
    dto.authorities=SecurityContextHolder.getContext().getAuthentication()
        .getAuthorities().stream()
        .map(a -> a.getAuthority())
        .toList();

    //<editor-fold desc="KeyCloak">
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    		dto.username = authentication.getName();
//    		dto.role = authentication.getSubRoles().iterator().next().getAuthority();
//    		dto.authorities = stripRolePrefix(authentication.getSubRoles());
        // Optional:
//    		KeycloakPrincipal<KeycloakSecurityContext> keycloakToken =(KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
//    		dto.fullName = keycloakToken.getKeycloakSecurityContext().getIdToken().getName();
//    		log.info("Other details about user from ID Token: " + keycloakToken.getKeycloakSecurityContext().getIdToken().getOtherClaims());
    //</editor-fold>
    return dto;
  }

  private Authentication f() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

    @Bean // enable propagation of SecurityContextHolder over @Async
  	public DelegatingSecurityContextAsyncTaskExecutor taskExecutor(ThreadPoolTaskExecutor executor) {
  		return new DelegatingSecurityContextAsyncTaskExecutor(executor);
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

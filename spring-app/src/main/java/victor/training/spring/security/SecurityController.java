package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SecurityController {
  private final AnotherClass anotherClass;

  @GetMapping("api/user/current")
  public CurrentUserDto getCurrentUsername() throws ExecutionException, InterruptedException {
    log.info("Return current user");
    CurrentUserDto dto = new CurrentUserDto();
    dto.username = anotherClass.metoda().get();
    // se propaga automat pe orice apel facut in THREADU CURENT

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


  //    	@Bean // enable propagation of SecurityContextHolder over @Async
  //    	public DelegatingSecurityContextAsyncTaskExecutor taskExecutor(ThreadPoolTaskExecutor executor) {
  //    		// https://www.baeldung.com/spring-security-async-principal-propagation
  //    		return new DelegatingSecurityContextAsyncTaskExecutor(executor);
  //    	}

  @Slf4j
  @Service
  public static class AnotherClass {
    @Async
    public CompletableFuture<String> metoda() {
      String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
      log.info("UPDATE ... WHER ... SET LAST_MODIFIED_BY="+currentUser);
      return CompletableFuture.completedFuture(currentUser);
    }
    //    @Async
    //    public CompletableFuture<String> asyncMethod() {
    //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //        log.info("Current authentication = {}", authentication);
    //        return CompletableFuture.completedFuture(authentication.getName());
    //    }
  }

}

package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.security.config.keycloak.KeyCloakUtils;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import javax.annotation.security.RunAs;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SecurityController {
  private final AnotherClass anotherClass;

  @Secured({"ROLE_USER", "RUN_AS_X"})
  @GetMapping("api/user/current")
  public CurrentUserDto getCurrentUsername(/*Principal principal*/) throws Exception {
    KeyCloakUtils.printTheTokens();

    log.info("Return current user");
    CurrentUserDto dto = new CurrentUserDto();

    // userul curent logat acum de pe THREADUL curent (folosind un Thread Local var pe sub)
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // daca pleci de pe thread (CompletableFuture.supplyAsync, call de @Async, executor.submit())
    dto.username = authentication.getName();

    // in Spring, un Principal (om,sistem) autheNtificat are:
    // 1) username
    // 2) o lista de stringuri (numite de Spring "authorities")
    //     - daca un authority incepe cu prefixul "ROLE_" atunci este considerat "rol" de Spring (istorica)
    //        pt roluri o sa ai metode dedicate de a-l testa: @Secured, @PreAuthorize('hasRole, hasRole in config
    //        pt restu de authorities, le testezi cu @PreAuthorize('hasAuthority

    // A) role-based security
//    dto.role = extractOneRole(authentication.getAuthorities());

    // B) authority-based security
    dto.authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    System.out.println("DTO:" + dto);
//    SecurityContextHolder.setContext(new SecurityContextImpl(new RunAsUserToken("xuser", null, null, List.of(new SimpleGrantedAuthority("ROLE_X")), SecurityContextHolder.getContext().getAuthentication().getClass())));
    anotherClass.m();

    //<editor-fold desc="KeyCloak">
    //		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //		dto.username = authentication.getName();
//    		dto.role = authentication.getAuthorities().iterator().next().getAuthority();
//    		dto.authorities = stripRolePrefix(authentication.getAuthorities());
    //    // Optional:
    //		KeycloakPrincipal<KeycloakSecurityContext> keycloakToken =(KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
    //		dto.fullName = keycloakToken.getKeycloakSecurityContext().getIdToken().getName();
    //		log.info("Other details about user from ID Token: " + keycloakToken.getKeycloakSecurityContext().getIdToken().getOtherClaims());
    //</editor-fold>
    return dto;
  }

  public static String extractOneRole(Collection<? extends GrantedAuthority> authorities) {
    // For Spring Security a "role" is an authority starting with "ROLE_"
    List<String> roles = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .filter(s -> s.startsWith("ROLE_"))
            .map(s -> s.substring("ROLE_".length()))
            .collect(Collectors.toList());
    if (roles.size() == 2) {
      log.debug("Even though Spring allows a user to have multiple roles, we wont :)");
      return "N/A";
    }
    if (roles.isEmpty()) {
      return null;
    }
    return roles.get(0);
  }


  //    	@Bean // enable propagation of SecurityContextHolder over @Async sau alte calluri catre alte threaduri
  //    	public DelegatingSecurityContextAsyncTaskExecutor taskExecutor(ThreadPoolTaskExecutor executor) {
  //    		// https://www.baeldung.com/spring-security-async-principal-propagation
  //    		return new DelegatingSecurityContextAsyncTaskExecutor(executor);
  //    	}

  @Slf4j
  @Service
  public static class AnotherClass {
    @Secured("ROLE_RUN_AS_X")
    public void m() {
      log.info("Running X");
    }
    //    @Async
    //    public CompletableFuture<String> asyncMethod() {
    //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //        log.info("Current authentication = {}", authentication);
    //        return CompletableFuture.completedFuture(authentication.getName());
    //    }
  }

}

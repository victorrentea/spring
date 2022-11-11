package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TechnicalController {
    private final AnotherClass anotherClass;
    private final Executor securityPropagatingExecutor;

    @GetMapping("api/user/current")
    public CurrentUserDto getCurrentUsername() throws Exception {
        //		JWTUtils.printTheTokens();

        log.info("Return current user");
        CurrentUserDto dto = new CurrentUserDto();
        // cum e posibil sa obt userul curent printr-o metoda statica !?!
        // -> e tinuta pe THREADUL CURENT
        dto.username = CompletableFuture.supplyAsync(() -> anotherClass.cineEsti(), securityPropagatingExecutor).get();

        // dto.username = anotherClass.asyncMethod().get();

        // A) role-based security
        //		dto.role = extractOneRole(authentication.getAuthorities());

        // B) authority-based security
        //		dto.authorities = authentication.getAuthorities().stream()
        //				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());

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

    //	public static String extractOneRole(Collection<? extends GrantedAuthority> authorities) {
    //		// For Spring Security (eg. hasRole) a role is an authority starting with "ROLE_"
    //		List<String> roles = authorities.stream()
    //				.map(GrantedAuthority::getAuthority)
    //				.filter(authority -> authority.startsWith("ROLE_"))
    //				.map(authority -> authority.substring("ROLE_".length()))
    //				.collect(Collectors.toList());
    //		if (roles.size() == 2) {
    //			log.debug("Even though Spring allows a user to have multiple roles, we wont :)");
    //			return "N/A";
    //		}
    //		if (roles.size() == 0) {
    //			return null;
    //		}
    //		return roles.get(0);
    //	}




    @Autowired
    private WelcomeInfo welcomeInfo;

    // TODO [SEC] allow unsecured access
    @GetMapping("unsecured/welcome")
    public WelcomeInfo showWelcomeInfo() {
        return welcomeInfo;
    }

    // TODO [SEC] URL-pattern restriction: admin/**
    @GetMapping("admin/launch")
    public String restart() {
        return "What does this red button do?     ... [Missile Launched]";
    }

}
@Configuration
class UnConfig {
    @Bean // enable propagation of SecurityContextHolder over @Async
    public Executor securityPropagatingExecutor(ThreadPoolTaskExecutor monitoredExecutor) {
        // https://www.baeldung.com/spring-security-async-principal-propagation
        return new DelegatingSecurityContextAsyncTaskExecutor(monitoredExecutor);
    }
}

@Slf4j
@Service
class AnotherClass {
//    @Async
    public String cineEsti() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    //    @Async
//    public CompletableFuture<String> asyncMethod() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("Current authentication = {}", authentication);
//        return CompletableFuture.completedFuture(authentication.getName());
//    }
}

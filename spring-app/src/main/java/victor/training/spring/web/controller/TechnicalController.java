package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Async
	public CompletableFuture<String> getUsername() {
		log.debug("Am i in a different thread here ?");
		return CompletableFuture.completedFuture(SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
@RequiredArgsConstructor
@RestController
public class TechnicalController {
	private final UserService service;

	private static final Logger log = LoggerFactory.getLogger(TechnicalController.class);

	@GetMapping("api/user/current")
	public CurrentUserDto getCurrentUsername() throws ExecutionException, InterruptedException {
//		JWTUtils.printTheTokens();

		CurrentUserDto dto = new CurrentUserDto();
		dto.role = SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities()
				.stream()
				.map(ga -> ga.toString().substring("ROLE_".length()))
				.findFirst().get();

		dto.username = service.getUsername().get();
		// SSO: KeycloakPrincipal<KeycloakSecurityContext>
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

	@PostConstruct
	public void enableSecurityContextPropagationOverAsyncCalls() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@Autowired
	private WelcomeInfo welcomeInfo;

	// TODO [SEC] allow unsecured access
	@GetMapping("unsecured/welcome")
	public WelcomeInfo showWelcomeInfo(){
		return welcomeInfo;
	}

	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("admin/launch")
	public String restart() {
		return "What does this red button do?     ... [Missile Launched]";
	}

}


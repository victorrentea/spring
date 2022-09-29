package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import java.util.concurrent.ExecutionException;

@Component
class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	// TODO victorrentea 2022-09-28: reactive
//	public CompletableFuture<String> getUsername() {
//		log.debug("Am i in a different thread here ?");
////		return CompletableFuture.completedFuture(RSCH.getContext().getAuthentication().getName());
//	}
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
		dto.username = "user";
//		Authentication authentication = SecurityContextHolder.getContext()
//				.getAuthentication();
//		dto.role = authentication.getAuthorities()
//				.stream()
//				.map(ga -> ga.toString().substring("ROLE_".length()))
//				.findFirst().get();
//
//		dto.username = service.getUsername().get();
//		// SSO: KeycloakPrincipal<KeycloakSecurityContext>
//		// A) role-based security
////		dto.role = extractOneRole(authentication.getAuthorities());
//		// B) authority-based security
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

	@GetMapping("admin/launch")
	public String restart() {
		return "What does this red button do?     ... [Missile Launched]";
	}

}

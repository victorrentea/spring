package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
public class TechnicalController {
		static ThreadLocal<String> t;
		private final SomeService someService;

	@GetMapping("api/user/current")
	public CurrentUserDto getCurrentUsername() throws ExecutionException, InterruptedException {
		CurrentUserDto dto = new CurrentUserDto();
		// SSO: KeycloakPrincipal<KeycloakSecurityContext>

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		KeycloakPrincipal<KeycloakSecurityContext> keycloakToken =
				(KeycloakPrincipal<KeycloakSecurityContext>) authentication
		.getPrincipal();
		System.out.println("access_token = " + keycloakToken.getKeycloakSecurityContext().getTokenString());
		dto.username = someService.deep().get();
		dto.role = authentication.getAuthorities().iterator().next().getAuthority();
//		dto.authorities = stripRolePrefix(authentication.getAuthorities());
		dto.authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList());
//keycloakToken.getKeycloakSecurityContext().getIdToken().getOtherClaims()
		System.out.println(authentication.getAuthorities());

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

		private List<String> stripRolePrefix(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream()
			.map(grantedAuthority -> grantedAuthority.getAuthority().substring("ROLE_".length()))
			.collect(toList());
	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() { // dear beloved baeldung (Eugen Paraschiv)
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	// TODO [SEC] allow unsecured access
	@GetMapping("unsecured/welcome")
	public String showWelcomeInfo(){
		return "Hello!";
	}

	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("admin/launch")
	public String restart() {
		return "What does this red button do?     ... [Missile Launched]";
	}

}


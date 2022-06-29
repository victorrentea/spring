package victor.training.spring.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TechnicalController {

	@GetMapping("api/user/current")
	public CurrentUserDto getCurrentUsername() {
		CurrentUserDto dto = new CurrentUserDto();
		// SSO: KeycloakPrincipal<KeycloakSecurityContext>

		printTheTokens();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		dto.username = username;
		// A) role-based security
//		dto.role = extractOneRole(authentication.getAuthorities());
		// B) authority-based security
		dto.authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());

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

	@SneakyThrows
	private void printTheTokens() {
		Object opaquePrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) opaquePrincipal;
		KeycloakSecurityContext keycloakSecurityContext = principal.getKeycloakSecurityContext();

		log.info("OpenID Connect Token:\n" + /*prettyPrintJson(*/keycloakSecurityContext.getIdTokenString());
		log.info("Access Token 👑:\n" + /*prettyPrintJson(*/keycloakSecurityContext.getTokenString());
	}

	private String prettyPrintJson(String idTokenString) throws JsonProcessingException {
		Object json = new ObjectMapper().readValue(idTokenString, Object.class);
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json);
	}

	private String extractOneRole(Collection<? extends GrantedAuthority> authorities) {
		// For Spring Security (eg. hasRole) a role is an authority starting with "ROLE_"
		System.out.println(authorities.toString());
		List<String> roles = authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.filter(authority -> authority.startsWith("ROLE_"))
				.map(authority -> authority.substring("ROLE_".length()))
				.collect(Collectors.toList());
		if (roles.size() == 2) {
			throw new IllegalArgumentException("Even though Spring allows a user to have multiple roles, we wont :)");
		}
		if (roles.size() == 0) {
			return null;
		}
		return roles.get(0);
	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsyncCalls() {
//		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
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


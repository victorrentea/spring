package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.controller.dto.LoggedInUserDto;
import victor.training.spring.web.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TechnicalController {
	private final UserService userService;

	@GetMapping("api/user/current")
	public LoggedInUserDto getCurrentUsername() {
		// TODO implement me

//		KeycloakPrincipal<KeycloakSecurityContext> principal = TODO SSO
//		SecurityUser principal = TODO UserPassword

		LoggedInUserDto dto = new LoggedInUserDto();

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();


		KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		IDToken idToken = principal.getKeycloakSecurityContext().getIdToken();
		String fullName = idToken.getGivenName() + " " + idToken.getFamilyName().toUpperCase();

		System.out.println("Other details: " + idToken.getOtherClaims());

		dto.username = fullName + " ("+currentUsername+")";
		dto.role = "";//authentication.getAuthorities().iterator().next().getAuthority();
		dto.authorities = Collections.emptyList();//authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList());
		return dto;
		// TODO How to propagate current user on thread over @Async calls?
	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() {
//		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	// TODO use authorities in FR
//	@GetMapping("api/user/current/authorities")
//	public List<String> getCurrentUserAuthorities() throws Exception {
//		SecurityUser securityUserOnSession = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		return securityUserOnSession.getAuthorities().stream()
//			.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//	}

//	@Autowired  // TODO @Import the other Spring Boot Application
	private WelcomeInfo welcomeInfo;

	// TODO [SEC] allow unsecured access
	@GetMapping("unsecured/welcome-info")
	public String showWelcomeInfo(){
		// TODO return welcomeInfo;
		return "Welcome! What's your temperature?";
	}

	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("admin/launch")
	public String restart() {
		return "What does this red button do?     ... [Missile Launched]";
	}
}

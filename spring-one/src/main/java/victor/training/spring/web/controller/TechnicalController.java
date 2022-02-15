package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.controller.dto.LoggedInUserDto;
import victor.training.spring.web.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TechnicalController {
	private final UserService userService;


//	public void methodInARepo() {
//		System.out.println("INSERT INTO .... CREATED_BY = " + username);
//	}

	@GetMapping("api/user/current")
	public LoggedInUserDto getCurrentUsername() {
		LoggedInUserDto dto = new LoggedInUserDto();
		// SSO: KeycloakPrincipal<KeycloakSecurityContext>

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//		dto.username = authentication.getName();
//		dto.role = authentication.getAuthorities().iterator().next().getAuthority();
//		dto.authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList());

//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		dto.username = authentication.getName();
		dto.role = authentication.getAuthorities().iterator().next().getAuthority();
		dto.authorities = stripRolePrefix(authentication.getAuthorities());

//    // Optional:

		Object principalOpaqueObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//actual type depends on the underlying authentication strategy. (token, basic, api token ..)

		KeycloakPrincipal<KeycloakSecurityContext> keycloakToken = (KeycloakPrincipal<KeycloakSecurityContext>) principalOpaqueObject;
		log.info("Extra data about the user from the token : " + keycloakToken.getKeycloakSecurityContext().getIdToken().getOtherClaims());
		return dto;
	}

	private List<String> stripRolePrefix(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream()
			.map(grantedAuthority -> grantedAuthority.getAuthority().substring("ROLE_".length()))
			.collect(toList());
	}

	// TODO propagate current user on thread over @Async calls?
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

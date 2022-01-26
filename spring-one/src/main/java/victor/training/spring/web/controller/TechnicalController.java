package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfoProps;
import victor.training.spring.web.controller.dto.LoggedInUserDto;
import victor.training.spring.web.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class TechnicalController {
	private final UserService userService;

	@GetMapping("api/user/current")
	public LoggedInUserDto getCurrentUsername() {
		LoggedInUserDto dto = new LoggedInUserDto();

		dto.username = SecurityContextHolder.getContext().getAuthentication().getName();
		dto.role = "";//authentication.getAuthorities().iterator().next().getAuthority();
		dto.authorities = Collections.emptyList();//authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList());

//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		dto.username = authentication.getName();
//		dto.role = authentication.getAuthorities().iterator().next().getAuthority();
//		dto.authorities = stripRolePrefix(authentication.getAuthorities());
//    // Optional:
//		KeycloakPrincipal<KeycloakSecurityContext> keycloakToken =(KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
//		dto.fullName = keycloakToken.getKeycloakSecurityContext().getIdToken().getName();
//		log.info("Other details about user from ID Token: " + keycloakToken.getKeycloakSecurityContext().getIdToken().getOtherClaims());
		return dto;
	}

//	private List<String> stripRolePrefix(Collection<? extends GrantedAuthority> authorities) {
//		return authorities.stream()
//			.map(grantedAuthority -> grantedAuthority.getAuthority().substring("ROLE_".length()))
//			.collect(toList());
//	}

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
	private WelcomeInfoProps welcomeInfoProps;

//	@CrossOrigin()
	// TODO [SEC] allow unsecured access
	@GetMapping("unsecured/welcome-info")
	public String showWelcomeInfo(){
		// TODO return welcomeInfo;
		return "Welcome! What's your temperature?";
	}

	// TODO [SEC] URL-pattern restriction: admin/**
	}

@RestController
@RequestMapping("admin")
@PreAuthorize("hasRole('ADMIN')")
class AdminController {
	@GetMapping("for-ms1")
	public String forOurClientMS() {
		return "What does this red button do?     ... [Missile Launched]";
	}
	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("launch")
	public String restart() {
		return "What does this red button do?     ... [Missile Launched]";
	}

}
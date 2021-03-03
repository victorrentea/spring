package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.security.SecurityUser;
import victor.training.spring.web.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
public class TechnicalController {
	@Autowired
	private UserService userService;

	@GetMapping("rest/user/current")
	public String getCurrentUsername() throws ExecutionException, InterruptedException {
		// TODO implement me
//		return SecurityContextHolder.getContext().getAuthentication().getName();
		// try getting the user from an Async task:
			return userService.getCurrentUsername().get(); // this only works due to the @PostConstruct below
	}
	@GetMapping("rest/user/current/authorities")
	public List<String> getCurrentUserAuthorities() throws ExecutionException, InterruptedException {
		SecurityUser securityUserOnSession = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return securityUserOnSession.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

//	@Autowired  // TODO Import the other Spring Boot Application
	private WelcomeInfo welcomeInfo;

	// TODO [SEC] allow unsecured access
	@GetMapping("public/welcome-info")
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

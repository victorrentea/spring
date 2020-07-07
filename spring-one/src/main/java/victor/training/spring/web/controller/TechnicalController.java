package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.security.SecurityUser;
import victor.training.spring.web.service.UserService;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

@RestController
public class TechnicalController {
	@Autowired
	private UserService userService;

	@GetMapping("rest/user/current")
	public String getCurrentUsername() {
		// TODO implement me
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecurityUser principal = (SecurityUser) authentication.getPrincipal();
		return authentication.getName() + " on " + principal.getManagedTeacherIds();
		// try getting the user from an Async task:
		//	return userService.getCurrentUsername().get(); // this only works due to the @PostConstruct below
	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() {
//		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@Autowired  // TODO Import the other Spring Boot Application
	private WelcomeInfo welcomeInfo;

	@GetMapping("unsecured/welcome-info")
	public WelcomeInfo showWelcomeInfo(){
		return welcomeInfo;
	}

	@GetMapping("ping")
	public String ping() {
		return "Pong " + getCurrentUsername();
	}

	@Autowired
	private ConfigurableApplicationContext context;
	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("admin/restart")
	public void restart() {
		context.refresh();
	}
}

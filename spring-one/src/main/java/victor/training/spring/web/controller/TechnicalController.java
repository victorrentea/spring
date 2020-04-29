package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.service.UserService;

import java.util.concurrent.ExecutionException;

@RestController
public class TechnicalController {
	@Autowired
	UserService userService;
	@GetMapping("rest/user/current")
	public String getCurrentUsername() throws ExecutionException, InterruptedException {
		// TODO implement me
		return userService.getCurrentUsername().get();
	}

	@PostMapping
			public void enable() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);


	}

	@Autowired  // TODO Import the other Spring Boot Application
	WelcomeInfo welcomeInfo;

	// TODO [SEC] allow unsecured access
	@GetMapping("unsecured/welcome-info")
	public WelcomeInfo showWelcomeInfo(){
		return welcomeInfo;
	}

	@GetMapping("beer")
	public void haveABeer() {
		// TODO [opt] propagate identity through async calls
		// https://www.baeldung.com/spring-security-async-principal-propagation
	}

	@GetMapping("ping")
	public String ping() throws ExecutionException, InterruptedException {
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

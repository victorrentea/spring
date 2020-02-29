package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.props.WelcomeInfo;

@RestController
public class TechnicalController {
	@GetMapping("rest/user/current")
	public String getCurrentUsername() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return "";
		}
		return "User: " + context.getAuthentication().getName();
	}

//	@Autowired  // TODO Import the other Spring Boot Application
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

	@Autowired
	private ConfigurableApplicationContext context;
	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("admin/restart")
	public void restart() {
		context.refresh();
	}
}

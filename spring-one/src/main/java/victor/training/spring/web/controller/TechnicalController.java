package victor.training.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
public class TechnicalController {
	@Autowired
	UserService userService;
	@GetMapping("rest/user/current")
	public String getCurrentUsername() throws ExecutionException, InterruptedException {
		// TODO implement me
//		return userService.getCurrentUsername().get(); // asta nu merge,
		// Poate merge: citeste aici: https://www.baeldung.com/spring-security-async-principal-propagation

		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@PostMapping
	public void enable() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);


	}

	@Autowired
	WelcomeInfo welcomeInfo;

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
		log.info("Hot-restarting spring application");
//		context.refresh();
	}

}

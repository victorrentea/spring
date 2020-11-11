package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.service.UserService;

import javax.annotation.PostConstruct;

@RestController
public class TechnicalController {
	@Autowired
	private UserService userService;

	@GetMapping("rest/user/current")
	public String getCurrentUsername() {
		// TODO implement me
		return "TODO:user";
		// try getting the user from an Async task:
		//	return userService.getCurrentUsername().get(); // this only works due to the @PostConstruct below
	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() {
//		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

//	@Autowired  // TODO Import the other Spring Boot Application
	private WelcomeInfo welcomeInfo;

	// TODO [SEC] allow unsecured access
	@GetMapping("unsecured/welcome-info")
	public String showWelcomeInfo(){
		// TODO return welcomeInfo;
		return "Welcome! What's your temperature?";
	}

	@GetMapping("ping")
	public String ping() {
		return "Pong " + getCurrentUsername();
	}

	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("admin/launch")
	public String restart() {
		return "What does this red button do?     ... [Missile Launched]";
	}
}

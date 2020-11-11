package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.security.SecurityUser;
import victor.training.spring.web.service.UserService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

@RestController
public class TechnicalController {
	@Autowired
	private UserService userService;

	@GetMapping("rest/user/current")
	public String getCurrentUsername() {
		SecurityUser securityUser = (SecurityUser) SecurityContextHolder
			.getContext().getAuthentication().getPrincipal();
		System.out.println("HIT");
//		ASTA NU: ci in loc, @PreAuthorize()
//		if (!securityUser.getPermissions().contains("deleteTraining")) {
//			throw new IllegalArgumentException();
//		}
		return securityUser.getUsername();
		// try getting the user from an Async task:
		//	return userService.getCurrentUsername().get(); // this only works due to the @PostConstruct below
	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() {
//		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

//	@Autowired  // TODO Import the other Spring Boot Application
	private WelcomeInfo welcomeInfo;



	@GetMapping("ping")
	public String ping() {
		return "Pong " + getCurrentUsername();
	}

	@Autowired
	private ConfigurableApplicationContext context;
	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("admin/launch-missile")
	public void restart() {
		System.out.println("Un buton rosu mare");
	}
}

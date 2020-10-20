package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.service.UserService;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

@RestController
public class TechnicalController {
	@Autowired
	private UserService userService;

	@GetMapping("rest/user/current")
	public String getCurrentUsername() throws ExecutionException, InterruptedException {
		// TODO implement me
		return userService.getCurrentUser().get();

		// REAL USE-CASE: executorul "limitat" are doar 5 threaduri.
		// De ce ? ca sa nu rupi providerul in doua

		// try getting the user from an Async task:
		//	return userService.getCurrentUsername().get(); // this only works due to the @PostConstruct below
	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}


	@GetMapping("ping")
	public String ping() throws ExecutionException, InterruptedException {
		return "Pong " + getCurrentUsername();
	}

	@Autowired
	private ConfigurableApplicationContext context;
	// TODO [SEC] URL-pattern restriction: admin/**

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("admin/restart")
	public void restart() {
//		context.refresh();
		System.out.println("TAMPENII");
	}
}

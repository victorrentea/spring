package victor.training.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.service.UserService;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
public class TechnicalController {
	@Autowired
	UserService userService;

	@GetMapping("rest/user/current")
	public String getCurrentUsername() throws ExecutionException, InterruptedException {
		// TODO implement me
		return SecurityContextHolder.getContext().getAuthentication().getName();
//		return userService.getCurrentUsername().get(); // this only works due to the @PostConstruct below
	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@Autowired
	private WelcomeInfo welcomeInfo;

	@GetMapping("unsecured/welcome-info")
	public WelcomeInfo showWelcomeInfo(){
		return welcomeInfo;
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

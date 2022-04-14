package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.CurrentUserDto;
import victor.training.spring.web.security.SecurityUser;
import victor.training.spring.web.service.TrainingService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class TechnicalController {
	public static final String API_TICKETS_COUNT_URL = "api/tickets/count";
	@Autowired
	private TrainingService trainingService;

	@GetMapping("api/user/current")
	public CurrentUserDto getCurrentUsername() throws ExecutionException, InterruptedException {
		CurrentUserDto dto = new CurrentUserDto();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		dto.username = trainingService.getCurrentUsername().get();

		SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

		dto.username = securityUser.getFullName() + "(" + securityUser.getUsername()+ ")";
		dto.role = securityUser.getRole().name();

		dto.authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());
		return dto;
	}
	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@CrossOrigin(originPatterns = "**")
	@GetMapping(API_TICKETS_COUNT_URL)
	public Long getRemainingTickets() {
		return 9L;
	}

//	private List<String> stripRolePrefix(Collection<? extends GrantedAuthority> authorities) {
//		return authorities.stream()
//			.map(grantedAuthority -> grantedAuthority.getAuthority().substring("ROLE_".length()))
//			.collect(toList());
//	}



	// TODO [SEC] allow unsecured access
	@GetMapping("unsecured/welcome")
	public String showWelcomeInfo(){
		return "Welcome. Here is the support phone in case you are unable to connect.";
	}

}
@RestController
@Secured("ROLE_ADMIN")
class AdminController {
	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("admin/launch")
	public String restart() {
		return "What does this red button do?     ... [Missile Launched]";
	}

	@GetMapping("admin/kill/one")
	public String kill() {
		return "kill";
	}

}

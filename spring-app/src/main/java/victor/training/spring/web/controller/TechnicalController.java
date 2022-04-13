package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import javax.annotation.PostConstruct;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class TechnicalController {

	@GetMapping("api/user/current")
	public CurrentUserDto getCurrentUsername() {
		CurrentUserDto dto = new CurrentUserDto();
		dto.username = "// TODO: get username";
		dto.role = "";
		dto.authorities = Collections.emptyList();
		return dto;
	}


	@CrossOrigin(originPatterns = "**")
	@GetMapping("api/tickets/count")
	public Long getRemainingTickets() {
		return 9L;
	}

//	private List<String> stripRolePrefix(Collection<? extends GrantedAuthority> authorities) {
//		return authorities.stream()
//			.map(grantedAuthority -> grantedAuthority.getAuthority().substring("ROLE_".length()))
//			.collect(toList());
//	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() {
//		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	// TODO [SEC] allow unsecured access
	@GetMapping("unsecured/welcome")
	public String showWelcomeInfo(){
		return "Welcome. Here is the support phone in case you are unable to connect.";
	}

	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("admin/launch")
	public String restart() {
		return "What does this red button do?     ... [Missile Launched]";
	}
}

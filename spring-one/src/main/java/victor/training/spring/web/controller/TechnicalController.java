package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.controller.dto.LoggedInUserDto;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.service.UserService;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class TechnicalController {
	private final UserService userService;

	@GetMapping("api/user/current")
	public LoggedInUserDto getCurrentUsername() {
		// TODO implement me
		LoggedInUserDto dto = new LoggedInUserDto();
		// SSO: KeycloakPrincipal<KeycloakSecurityContext>
//		HttpServletRequest request;
//		request.getUserPrincipal()

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		dto.username = name;
		dto.role = "";//authentication.getAuthorities().iterator().next().getAuthority();
		dto.authorities = Collections.emptyList();//authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList());
		return dto;
		// TODO How to propagate current user on thread over @Async calls?
	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsync() {
//		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	// TODO use authorities in FR
//	@GetMapping("api/user/current/authorities")
//	public List<String> getCurrentUserAuthorities() throws Exception {
//		SecurityUser securityUserOnSession = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		return securityUserOnSession.getAuthorities().stream()
//			.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//	}

//	@Autowired  // TODO @Import the other Spring Boot Application
	private WelcomeInfo welcomeInfo;

	// JavaEE: Principal 
//	Principal principle;
//	{
//		principle.getName();
//	}
	
//	@GET // JAX-RS modul JavaEE de a implementa servicii REST
//	@Resource
	public void method(Principal principal) {
		
	}
	
	// TODO [SEC] allow unsecured access
	@GetMapping("unsecured/welcome-info")
	public String showWelcomeInfo(){
		// TODO return welcomeInfo;
		return "Welcome! What's your temperature?";
	}
	@GetMapping("unsecured/gdpr")
	public String gdprData(){
		return "Welcome! What's your temperature?";
	}
	@GetMapping("unsecured/training/{uuid}")
	public TrainingDto getTrainingUnsecured(@PathVariable String uuid){
		return new TrainingDto(trainingRepo.findByExternalUUID(uuid));
	}

	private final TrainingRepo trainingRepo;




	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("admin/launch")
	public String restart() {
		return "What does this red button do?     ... [Missile Launched]";
	}
}

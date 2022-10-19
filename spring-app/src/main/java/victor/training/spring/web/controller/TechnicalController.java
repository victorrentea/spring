package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.controller.dto.CurrentUserDto;
import victor.training.spring.web.security.JWTUtils;

import javax.annotation.PostConstruct;
import javax.annotation.security.RunAs;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Configuration
class ConfiguMeu {
	@Bean
	public ThreadPoolTaskExecutor executorCePropagaUserul() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); // de ob pus in context ca @Bean
		executor.setMaxPoolSize(2);
		executor.setCorePoolSize(2);
		executor.setTaskDecorator(new TaskDecorator() {
			@Override
			public Runnable decorate(Runnable originalTask) {
				// rulez in threadul vechi (calleru)
				SecurityContext context = SecurityContextHolder.getContext();
				return ()-> {
					// restore user on thread --- rulez in threadul nou
					SecurityContextHolder.setContext(context);
					try {
						originalTask.run();
					} finally {
						// ca altfel alt task dupa mine ar putea gasi credentialele altui user!!!
						SecurityContextHolder.clearContext();
					}
				};
			}
		});
		executor.initialize();
		return executor;
	}
}

@Slf4j
@RequiredArgsConstructor
@RestController
public class TechnicalController {

	// ce e aia :
	// @Gill: sunt holurile normale de apeluri de functii decente, la vedere, cu param
	// si mai exista si aerisirea (canalu secret) prin care poti pasa date dintr-o functie in alta
	//   cata vreme ramai pe acelasi thread.
	// de ce ne doare? unde sunt folsoite astea ?
	//    1 SecurityContextHolder.getContext()
	//    2 JDBC Connection tine starea connex pe threadlocal ==> nu pot scrie pe aceeasi DB conn din 2 threaduri
	//    3 @Transactional - rranzactia curenta
	//    4 Logback MDC   %X google
	//    5 @Scope("request") pe care pui storeId / tenantId
	 private static final ThreadLocal<String> dateleMele = new ThreadLocal<>();

	public void method() {
//		ThreadUtils.sleepq(3000);
		log.info("Oare chiar " + dateleMele.get());

	}


//	@PostMapping("/prefs")
////	@PreAuthorize("principal.username == #username")
//	public void setPrefs(@RequestBody String noilePref, Principal principal) {
//		String username = principal.getName();
//
//	}

	@Autowired
	ThreadPoolTaskExecutor executorCePropagaUserul;

	@GetMapping("api/user/current")
	public CurrentUserDto getCurrentUsername(HttpSession session) throws ExecutionException, InterruptedException {
		JWTUtils.printTheTokens();
//		log.debug("Cookie " + session.getId());
//		session.invalidate();

		dateleMele.set("sunt bou " + LocalDateTime.now());
		log.info("eu chiar " + dateleMele);
		method();

		CurrentUserDto dto = new CurrentUserDto();

		dto.username = CompletableFuture.supplyAsync(() ->
								SecurityContextHolder.getContext().getAuthentication().getName(),
						executorCePropagaUserul)
				.get(); // like a bull


		// A) role-based security
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		dto.role = extractOneRole(authentication.getAuthorities());

		// B) authority-based security
//		List<String>  [ "ROLE_ADMIN", "training.delete"
		dto.authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());

		//<editor-fold desc="KeyCloak">
		//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		dto.username = authentication.getName();
//		dto.role = authentication.getAuthorities().iterator().next().getAuthority();
//		dto.authorities = stripRolePrefix(authentication.getAuthorities());
//    // Optional:
//		KeycloakPrincipal<KeycloakSecurityContext> keycloakToken =(KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
//		dto.fullName = keycloakToken.getKeycloakSecurityContext().getIdToken().getName();
//		log.info("Other details about user from ID Token: " + keycloakToken.getKeycloakSecurityContext().getIdToken().getOtherClaims());
		//</editor-fold>
		return dto;
	}

	public static String extractOneRole(Collection<? extends GrantedAuthority> authorities) {
		// For Spring Security (eg. hasRole) a role is an authority starting with "ROLE_"
		List<String> roles = authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.filter(authority -> authority.startsWith("ROLE_"))
				.map(authority -> authority.substring("ROLE_".length()))
				.collect(Collectors.toList());
		if (roles.size() == 2) {
			log.debug("Even though Spring allows a user to have multiple roles, we wont :)");
			return "N/A";
		}
		if (roles.size() == 0) {
			return null;
		}
		return roles.get(0);
	}

	@PostConstruct
	public void enableSecurityContextPropagationOverAsyncCalls() {
		// de la baeldung.com citire pt @Async calls.
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@Autowired
	private WelcomeInfo welcomeInfo;

	// TODO [SEC] allow unsecured access
	@GetMapping("unsecured/welcome")
	public WelcomeInfo showWelcomeInfo(){
		return welcomeInfo;
	}

	// TODO [SEC] URL-pattern restriction: admin/**
	@GetMapping("admin/launch")
	public String restart() {
		return "What does this red button do?     ... [Missile Launched]";
	}

}

